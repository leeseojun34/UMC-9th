-- 기존 데이터 삭제
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE user_mission;
TRUNCATE TABLE user_food;
TRUNCATE TABLE user_term;
TRUNCATE TABLE review_image;
TRUNCATE TABLE review;
TRUNCATE TABLE inquiry_image;
TRUNCATE TABLE inquiry_reply;
TRUNCATE TABLE inquiry;
TRUNCATE TABLE reply;
TRUNCATE TABLE mission;
TRUNCATE TABLE restaurant;
TRUNCATE TABLE region;
TRUNCATE TABLE food;
TRUNCATE TABLE term;
TRUNCATE TABLE user;

SET FOREIGN_KEY_CHECKS = 1;

-- ========================================
-- 1. 지역 데이터
-- ========================================
INSERT INTO region (id, name, created_at, updated_at, is_activated) VALUES
(1, '서울 강남구', NOW(), NOW(), true),
(2, '서울 서초구', NOW(), NOW(), true),
(3, '서울 송파구', NOW(), NOW(), true),
(4, '경기 성남시', NOW(), NOW(), true),
(5, '부산 해운대구', NOW(), NOW(), true);

-- ========================================
-- 2. 사용자 데이터
-- ========================================
INSERT INTO user (id, name, nickname, email, gender, birth, phone, profile_image_url, total_point, address, provider, status, created_at, updated_at, is_activated) VALUES
(1, '김철수', '철수킴', 'chulsoo@example.com', 'MALE', '1995-03-15', '010-1234-5678', 'https://example.com/profile1.jpg', 1000, '서울시 강남구', 'KAKAO', 'ACTIVE', NOW(), NOW(), true),
(2, '이영희', '영희리', 'younghee@example.com', 'FEMALE', '1998-07-22', '010-2345-6789', 'https://example.com/profile2.jpg', 2500, '서울시 서초구', 'GOOGLE', 'ACTIVE', NOW(), NOW(), true),
(3, '박민수', '민수박', 'minsoo@example.com', 'MALE', '1993-11-08', '010-3456-7890', 'https://example.com/profile3.jpg', 500, '서울시 송파구', 'NAVER', 'ACTIVE', NOW(), NOW(), true),
(4, '최지은', '지은최', 'jieun@example.com', 'FEMALE', '2000-05-30', '010-4567-8901', 'https://example.com/profile4.jpg', 3000, '경기도 성남시', 'KAKAO', 'ACTIVE', NOW(), NOW(), true),
(5, '정대호', '대호정', 'daeho@example.com', 'MALE', '1997-09-12', '010-5678-9012', 'https://example.com/profile5.jpg', 1500, '부산시 해운대구', 'GOOGLE', 'ACTIVE', NOW(), NOW(), true);

-- ========================================
-- 3. 음식 카테고리 데이터
-- ========================================
INSERT INTO food (id, name, created_at, updated_at, is_activated) VALUES
(1, 'KOREAN', NOW(), NOW(), true),
(2, 'CHINESE', NOW(), NOW(), true),
(3, 'JAPANESE', NOW(), NOW(), true),
(4, 'WESTERN', NOW(), NOW(), true),
(5, 'ASIAN', NOW(), NOW(), true),
(6, 'FAST_FOOD', NOW(), NOW(), true),
(7, 'CAFE', NOW(), NOW(), true);

-- ========================================
-- 4. 사용자-음식 선호도 (UserFood) - Set 중복 방지 테스트
-- ========================================
INSERT INTO user_food (id, user_id, food_id, created_at, updated_at, is_activated) VALUES
(1, 1, 1, NOW(), NOW(), true),  -- 김철수 - 한식
(2, 1, 3, NOW(), NOW(), true),  -- 김철수 - 일식
(3, 2, 2, NOW(), NOW(), true),  -- 이영희 - 중식
(4, 2, 4, NOW(), NOW(), true),  -- 이영희 - 양식
(5, 3, 1, NOW(), NOW(), true),  -- 박민수 - 한식
(6, 4, 5, NOW(), NOW(), true),  -- 최지은 - 아시안
(7, 5, 6, NOW(), NOW(), true);  -- 정대호 - 패스트푸드

-- ========================================
-- 5. 약관 데이터
-- ========================================
INSERT INTO term (id, name, content, is_required, created_at, updated_at, is_activated) VALUES
(1, 'SERVICE', '서비스 이용약관에 동의합니다.', true, NOW(), NOW(), true),
(2, 'PRIVACY', '개인정보 처리방침에 동의합니다.', true, NOW(), NOW(), true),
(3, 'MARKETING', '마케팅 정보 수신에 동의합니다.', false, NOW(), NOW(), true),
(4, 'LOCATION', '위치 기반 서비스 이용에 동의합니다.', false, NOW(), NOW(), true);

-- ========================================
-- 6. 사용자-약관 동의 (UserTerm) - orphanRemoval 테스트
-- ========================================
INSERT INTO user_term (id, user_id, term_id, is_agree, created_at, updated_at, is_activated) VALUES
(1, 1, 1, true, NOW(), NOW(), true),
(2, 1, 2, true, NOW(), NOW(), true),
(3, 1, 3, true, NOW(), NOW(), true),
(4, 2, 1, true, NOW(), NOW(), true),
(5, 2, 2, true, NOW(), NOW(), true),
(6, 3, 1, true, NOW(), NOW(), true),
(7, 3, 2, true, NOW(), NOW(), true),
(8, 3, 4, true, NOW(), NOW(), true),
(9, 4, 1, true, NOW(), NOW(), true),
(10, 4, 2, true, NOW(), NOW(), true);

-- ========================================
-- 7. 레스토랑 데이터
-- ========================================
INSERT INTO restaurant (id, region_id, name, image_url, address, identification_number, rating, created_at, updated_at, is_activated) VALUES
(1, 1, '강남 한우 명가', 'https://example.com/restaurant1.jpg', '서울시 강남구 테헤란로 123', 1234567890, 4.5, NOW(), NOW(), true),
(2, 1, '이태리 파스타 전문점', 'https://example.com/restaurant2.jpg', '서울시 강남구 강남대로 456', 2345678901, 4.2, NOW(), NOW(), true),
(3, 2, '서초 일식당', 'https://example.com/restaurant3.jpg', '서울시 서초구 서초대로 789', 3456789012, 4.7, NOW(), NOW(), true),
(4, 3, '송파 중화요리', 'https://example.com/restaurant4.jpg', '서울시 송파구 올림픽로 321', 4567890123, 4.0, NOW(), NOW(), true),
(5, 4, '성남 카페거리', 'https://example.com/restaurant5.jpg', '경기도 성남시 분당구 정자동', 5678901234, 4.3, NOW(), NOW(), true);

-- ========================================
-- 8. 미션 데이터
-- ========================================
INSERT INTO mission (id, restaurant_id, title, requirement, status, reward, created_at, updated_at, is_activated) VALUES
(1, 1, '강남 한우 명가 리뷰 작성', '리뷰와 사진 1장 이상 등록', 'IN_PROGRESS', 1000, NOW(), NOW(), true),
(2, 1, '강남 한우 명가 단골 되기', '3회 이상 방문 후 리뷰 작성', 'IN_PROGRESS', 3000, NOW(), NOW(), true),
(3, 2, '파스타 전문점 첫 방문 리뷰', '첫 방문 리뷰와 메뉴 사진 등록', 'IN_PROGRESS', 500, NOW(), NOW(), true),
(4, 3, '서초 일식당 리뷰 작성', '별점 4점 이상 리뷰 작성', 'IN_PROGRESS', 1500, NOW(), NOW(), true),
(5, 4, '송파 중화요리 방문 인증', '가게 방문 사진 인증', 'IN_PROGRESS', 800, NOW(), NOW(), true),
(6, 5, '성남 카페거리 음료 리뷰', '음료 사진과 리뷰 작성', 'IN_PROGRESS', 700, NOW(), NOW(), true);

-- ========================================
-- 9. 사용자-미션 (UserMission) - 동시성 제어 테스트용
-- ========================================
INSERT INTO user_mission (id, user_id, mission_id, status, created_at, updated_at, is_activated, version) VALUES
(1, 1, 1, 'IN_PROGRESS', NOW(), NOW(), true, 0),
(2, 1, 2, 'COMPLETED', NOW(), NOW(), true, 0),
(3, 2, 3, 'IN_PROGRESS', NOW(), NOW(), true, 0),
(4, 3, 4, 'IN_PROGRESS', NOW(), NOW(), true, 0),
(5, 4, 5, 'COMPLETED', NOW(), NOW(), true, 0);

-- ========================================
-- 10. 리뷰 데이터
-- ========================================
INSERT INTO review (id, user_id, restaurant_id, reply_id, content, rating, created_at, updated_at, is_activated) VALUES
(1, 1, 1, NULL, '정말 맛있는 한우였습니다! 서비스도 친절하고 분위기도 좋았어요.', 4.5, NOW(), NOW(), true),
(2, 2, 2, NULL, '파스타가 정말 맛있어요. 면이 쫄깃하고 소스가 일품입니다.', 4.2, NOW(), NOW(), true),
(3, 3, 3, NULL, '신선한 회와 정성스러운 상차림에 감동했습니다.', 4.8, NOW(), NOW(), true),
(4, 4, 4, NULL, '짬뽕이 얼큰하고 맛있어요. 양도 푸짐합니다.', 4.0, NOW(), NOW(), true),
(5, 1, 2, NULL, '두 번째 방문인데 여전히 맛있네요!', 4.3, NOW(), NOW(), true),
(6, 2, 1, NULL, '특별한 날 방문하기 좋은 곳입니다.', 4.6, NOW(), NOW(), true);

-- ========================================
-- 11. 리뷰 이미지 데이터
-- ========================================
INSERT INTO review_image (id, review_id, url, created_at, updated_at, is_activated) VALUES
(1, 1, 'https://example.com/review1-1.jpg', NOW(), NOW(), true),
(2, 1, 'https://example.com/review1-2.jpg', NOW(), NOW(), true),
(3, 2, 'https://example.com/review2-1.jpg', NOW(), NOW(), true),
(4, 3, 'https://example.com/review3-1.jpg', NOW(), NOW(), true),
(5, 3, 'https://example.com/review3-2.jpg', NOW(), NOW(), true),
(6, 3, 'https://example.com/review3-3.jpg', NOW(), NOW(), true);

-- ========================================
-- 12. 식당 답변 데이터
-- ========================================
INSERT INTO reply (id, restaurant_id, content, created_at, updated_at, is_activated) VALUES
(1, 1, '감사합니다! 앞으로도 더 좋은 서비스로 보답하겠습니다.', NOW(), NOW(), true),
(2, 2, '소중한 리뷰 감사드립니다. 다음에도 방문해주세요!', NOW(), NOW(), true),
(3, 3, '즐거운 시간 보내셨다니 기쁩니다. 또 뵙겠습니다.', NOW(), NOW(), true);

-- 리뷰와 답변 연결
UPDATE review SET reply_id = 1 WHERE id = 1;
UPDATE review SET reply_id = 2 WHERE id = 2;
UPDATE review SET reply_id = 3 WHERE id = 3;

-- ========================================
-- 13. 문의 데이터
-- ========================================
INSERT INTO inquiry (id, user_id, title, content, status, created_at, updated_at, is_activated) VALUES
(1, 1, '포인트 적립 문의', '포인트가 제대로 적립되지 않았습니다.', 'COMPLETED', NOW(), NOW(), true),
(2, 2, '미션 완료 기준 문의', '미션 완료 기준이 궁금합니다.', 'PENDING', NOW(), NOW(), true),
(3, 3, '회원 탈퇴 방법', '회원 탈퇴는 어떻게 하나요?', 'IN_PROGRESS', NOW(), NOW(), true),
(4, 4, '가게 정보 수정 요청', '가게 주소가 변경되었습니다.', 'PENDING', NOW(), NOW(), true);

-- ========================================
-- 14. 문의 답변 데이터
-- ========================================
INSERT INTO inquiry_reply (id, inquiry_id, content, created_at, updated_at, is_activated) VALUES
(1, 1, '확인 결과 시스템 오류로 포인트가 적립되지 않았습니다. 포인트를 수동으로 지급해드렸습니다.', NOW(), NOW(), true),
(2, 3, '회원 탈퇴는 설정 > 계정관리 메뉴에서 가능합니다. 탈퇴 시 모든 데이터가 삭제되니 신중히 결정해주세요.', NOW(), NOW(), true);

-- ========================================
-- 15. 문의 이미지 데이터
-- ========================================
INSERT INTO inquiry_image (id, inquiry_id, url, created_at, updated_at, is_activated) VALUES
(1, 1, 'https://example.com/inquiry1-1.jpg', NOW(), NOW(), true),
(2, 1, 'https://example.com/inquiry1-2.jpg', NOW(), NOW(), true),
(3, 4, 'https://example.com/inquiry4-1.jpg', NOW(), NOW(), true);

-- ========================================
-- 데이터 삽입 완료 확인
-- ========================================
SELECT 
    'region' as table_name, COUNT(*) as count FROM region
UNION ALL SELECT 'user', COUNT(*) FROM user
UNION ALL SELECT 'food', COUNT(*) FROM food
UNION ALL SELECT 'user_food', COUNT(*) FROM user_food
UNION ALL SELECT 'term', COUNT(*) FROM term
UNION ALL SELECT 'user_term', COUNT(*) FROM user_term
UNION ALL SELECT 'restaurant', COUNT(*) FROM restaurant
UNION ALL SELECT 'mission', COUNT(*) FROM mission
UNION ALL SELECT 'user_mission', COUNT(*) FROM user_mission
UNION ALL SELECT 'review', COUNT(*) FROM review
UNION ALL SELECT 'review_image', COUNT(*) FROM review_image
UNION ALL SELECT 'reply', COUNT(*) FROM reply
UNION ALL SELECT 'inquiry', COUNT(*) FROM inquiry
UNION ALL SELECT 'inquiry_reply', COUNT(*) FROM inquiry_reply
UNION ALL SELECT 'inquiry_image', COUNT(*) FROM inquiry_image;