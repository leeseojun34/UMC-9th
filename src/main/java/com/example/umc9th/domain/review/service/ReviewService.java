package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.restaurant.entity.QRestaurant;
import com.example.umc9th.domain.restaurant.entity.Restaurant;
import com.example.umc9th.domain.review.dto.MyReviewRequestDTO;
import com.example.umc9th.domain.review.dto.MyReviewResponseDTO;
import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.user.entity.User;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Review createReview(User user, Restaurant restaurant, String content, Float rating) {
        Review review = Review.builder()
            .user(user)
            .restaurant(restaurant)
            .content(content)
            .rating(rating)
            .build();

        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public List<MyReviewResponseDTO> getMyReviews(
            Long userId, 
            String restaurantName, 
            Integer rating, 
            String filterType
    ) {
        MyReviewRequestDTO requestDTO = new MyReviewRequestDTO();
        requestDTO.setRestaurantName(restaurantName);
        requestDTO.setRating(rating);
        requestDTO.setFilterType(filterType);
        
        QReview review = QReview.review;
        QRestaurant restaurant = QRestaurant.restaurant;
        
        BooleanBuilder builder = new BooleanBuilder();

        // 식당 이름 필터링
        if ("restaurant".equals(filterType) && requestDTO.getRestaurantName() != null) {
            builder.and(restaurant.name.contains(requestDTO.getRestaurantName()));
        }
        // 별점 필터링
        else if ("rating".equals(filterType) && requestDTO.getRating() != null) {
            Integer ratingValue = requestDTO.getRating();
            builder.and(review.rating.goe(ratingValue.floatValue()))
                   .and(review.rating.lt((ratingValue + 1)));
        }
        // 식당 이름 + 별점 필터링
        else if ("both".equals(filterType)) {
            if (requestDTO.getRestaurantName() != null) {
                builder.and(restaurant.name.contains(requestDTO.getRestaurantName()));
            }
            if (requestDTO.getRating() != null) {
                Integer ratingValue = requestDTO.getRating();
                builder.and(review.rating.goe(ratingValue.floatValue()))
                       .and(review.rating.lt((ratingValue + 1)));
            }
        }
        // 필터링이 없을 경우 모든 리뷰 조회
        
        return reviewRepository.findMyReviews(userId, builder);
    }
}
