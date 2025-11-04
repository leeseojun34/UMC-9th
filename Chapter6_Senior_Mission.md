# 가게 검색 API

## 1. 개요

사용자의 요구사항에 맞춰 **지역, 가게 이름, 정렬 기준**을 동적으로 조합하여 가게를 검색하는 API를 구현

복잡한 조건의 동적 쿼리를 처리하기 위해 **QueryDSL**을 사용

## 2. API 명세

* **Method:** `GET`
* **Endpoint:** `/api/restaurants/search`
* **Parameters:**

| Parameter | Type | Required | Default | Description |
| :--- | :--- | :--- | :--- | :--- |
| `region` | String | No | - | 필터링할 지역명 (예: "강남구") |
| `keyword` | String | No | - | 검색할 가게 이름 키워드 |
| `sortBy` | String | No | `latest` | 정렬 기준 (`latest`: 최신순, `name`: 이름순) |
| `page` | Integer | No | `0` | 페이지 번호 (0부터 시작) |
| `size` | Integer | No | `10` | 페이지 당 아이템 개수 |

* **Response:** (커스텀 페이징 DTO)
  ```json
  {
    "isSuccess": true,
    "code": "OK",
    "message": "요청에 성공했습니다.",
    "result": {
      "items": [
        {
          "restaurantId": 1,
          "name": "민트초코 떡볶이",
          "regionName": "강남구",
          "address": "서울시 강남구...",
          "rating": 4.5,
          "createdAt": "2025-11-04T10:30:00"
        }
      ],
      "pageNo": 0,
      "numOfRows": 10,
      "totalCount": 1,
      "totalPages": 1
    }
  }
  ```

-----

## 3. 핵심 구현 내용

### 3.1 QueryDSL 도입

요구사항의 필터링, 검색, 정렬 조건이 모두 optional이므로, 컴파일 타임에 쿼리 오류를 잡을 수 있고 동적 쿼리 작성이 용이한 **QueryDSL**을 도입

1.  **`RestaurantQueryDsl` (인터페이스):**
    * 검색 전용 메서드(`searchRestaurants`) 정의
2.  **`RestaurantQueryDslImpl` (구현체):**
    * `JPAQueryFactory`를 사용하여 실제 QueryDSL 로직 구현
3.  **`RestaurantRepository` (메인 레포지토리):**
    * `JpaRepository<Restaurant, Long>`와 `RestaurantQueryDsl` 인터페이스를 다중 상속
    * 이를 통해 Spring Data JPA가 기본 CRUD 기능과 QueryDSL 커스텀 구현을 자동으로 통합하여 `RestaurantService`에서 `restaurantRepository.searchRestaurants()` 호출 가능

### 3.2 동적 쿼리 구현 (`RestaurantQueryDslImpl.java`)

`BooleanBuilder`를 사용하여 `where` 절의 조건을 동적으로 조립

#### 1. 지역(Region) 기반 필터링

* `regionName` 파라미터가 `null`이 아닐 경우에만 `where` 절에 추가
* `Restaurant`과 `Region` 엔티티가 `@ManyToOne` 관계이므로, `leftJoin`을 사용하여 `region.name`을 기준으로 필터링

<!-- end list -->

```java
// ...
QRestaurant restaurant = QRestaurant.restaurant;
QRegion region = QRegion.region;

BooleanBuilder builder = new BooleanBuilder();

if (regionName != null && !regionName.trim().isEmpty()) {
    builder.and(region.name.eq(regionName));
}

// ...
.from(restaurant)
.leftJoin(restaurant.region, region)
.where(builder)
// ...
```

#### 2\. 이름(Keyword) 검색

요구사항에 맞춰 키워드의 **공백 포함 여부**에 따라 로직을 분기

* **공백 포함 시 (예: "민트 초코")**:
    * `split("\\s+")`로 키워드를 공백 기준 분리
    * 별도의 `BooleanBuilder`인 `orBuilder`를 사용하여, 각 단어가 `restaurant.name`에 포함되는 경우를 `OR` 조건으로 묶음 (합집합)
* **공백 미포함 시 (예: "민트초코")**:
    * 키워드 전체가 `restaurant.name`에 포함되는 경우(`contains`)를 `AND` 조건으로 추가

<!-- end list -->

```java
if (keyword != null && !keyword.trim().isEmpty()) {
    if (keyword.contains(" ")) { // 1. 공백 포함 시
        String[] words = keyword.split("\\s+");
        BooleanBuilder orBuilder = new BooleanBuilder();
        for (String word : words) {
            orBuilder.or(restaurant.name.contains(word));
        }
        builder.and(orBuilder);
    } else { // 2. 공백 미포함 시
        builder.and(restaurant.name.contains(keyword));
    }
}
```

#### 3\. 동적 정렬 (Sort)

* `List<OrderSpecifier<?>>`를 사용하여 정렬 조건을 동적 관리
* `sortBy` 파라미터가 `"name"`인 경우:
    1.  `restaurant.name.asc()` (이름순)
    2.  `restaurant.createdAt.desc()` (이름 동일 시 최신순)
* 그 외의 경우 (기본값 `"latest"`):
    1.  `restaurant.createdAt.desc()` (최신순)

<!-- end list -->

```java
List<OrderSpecifier<?>> orders = new ArrayList<>();
if ("name".equals(sortBy)) {
    orders.add(restaurant.name.asc());
    orders.add(restaurant.createdAt.desc());
} else {
    orders.add(restaurant.createdAt.desc());
}

// ...
.orderBy(orders.toArray(new OrderSpecifier[0]))
// ...
```

### 3.3 페이징 처리 및 응답 DTO 커스텀

#### 페이징 쿼리 분리 (성능 최적화)

**데이터 조회 쿼리**와 **전체 카운트 조회 쿼리**를 명시적으로 분리하여 실행

1.  **Content 쿼리**: `select(...)`, `offset()`, `limit()`을 적용하여 실제 데이터 목록을 조회
2.  **Total 쿼리**: `select(restaurant.count())`를 사용하여 `where` 조건만 동일하게 적용하고 전체 개수를 조회

<!-- end list -->

```java
// 1. Content 쿼리
List<RestaurantSearchResponseDTO> content = queryFactory
    .select(...)
    .from(...)
    .where(builder)
    .orderBy(...)
    .offset(pageable.getOffset())
    .limit(pageable.getPageSize())
    .fetch();

// 2. Total 쿼리
Long total = queryFactory
    .select(restaurant.count())
    .from(restaurant)
    .leftJoin(restaurant.region, region) // join, where 조건은 동일해야 함
    .where(builder)
    .fetchOne();

// 3. Page 객체로 조합
return new PageImpl<>(content, pageable, total != null ? total : 0L);
```
