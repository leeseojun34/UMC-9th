package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.restaurant.entity.Restaurant;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
