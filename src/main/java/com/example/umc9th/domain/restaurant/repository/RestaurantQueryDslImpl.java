package com.example.umc9th.domain.restaurant.repository;

import com.example.umc9th.domain.region.entity.QRegion;
import com.example.umc9th.domain.restaurant.dto.RestaurantSearchResponseDTO;
import com.example.umc9th.domain.restaurant.entity.QRestaurant;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RestaurantQueryDslImpl implements RestaurantQueryDsl {

    private final EntityManager em;

    @Override
    public Page<RestaurantSearchResponseDTO> searchRestaurants(
        String regionName,
        String keyword,
        String sortBy,
        Pageable pageable
    ) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QRestaurant restaurant = QRestaurant.restaurant;
        QRegion region = QRegion.region;

        BooleanBuilder builder = new BooleanBuilder();

        if (regionName != null && !regionName.trim().isEmpty()) {
            builder.and(region.name.eq(regionName));
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            if (keyword.contains(" ")) {
                String[] words = keyword.split("\\s+");
                BooleanBuilder orBuilder = new BooleanBuilder();
                for (String word : words) {
                    orBuilder.or(restaurant.name.contains(word));
                }
                builder.and(orBuilder);
            } else {
                builder.and(restaurant.name.contains(keyword));
            }
        }

        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if ("name".equals(sortBy)) {
            orders.add(restaurant.name.asc());
            orders.add(restaurant.createdAt.desc());
        } else {
            orders.add(restaurant.createdAt.desc());
        }

        List<RestaurantSearchResponseDTO> content = queryFactory
            .select(Projections.constructor(
                RestaurantSearchResponseDTO.class,
                restaurant.id,
                restaurant.name,
                region.name,
                restaurant.address,
                restaurant.rating,
                restaurant.createdAt
            ))
            .from(restaurant)
            .leftJoin(restaurant.region, region)
            .where(builder)
            .orderBy(orders.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(restaurant.count())
            .from(restaurant)
            .leftJoin(restaurant.region, region)
            .where(builder)
            .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }
}
