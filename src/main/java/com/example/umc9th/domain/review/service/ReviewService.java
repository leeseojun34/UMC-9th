package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.restaurant.entity.QRestaurant;
import com.example.umc9th.domain.restaurant.entity.Restaurant;
import com.example.umc9th.domain.restaurant.exception.RestaurantErrorCode;
import com.example.umc9th.domain.restaurant.exception.RestaurantException;
import com.example.umc9th.domain.restaurant.repository.RestaurantRepository;
import com.example.umc9th.domain.review.dto.request.MyReviewRequestDTO;
import com.example.umc9th.domain.review.dto.request.ReviewRequestDTO;
import com.example.umc9th.domain.review.dto.response.MyReviewResponseDTO;
import com.example.umc9th.domain.review.dto.response.ReviewPreViewListDTO;
import com.example.umc9th.domain.review.dto.response.ReviewResponseDTO;
import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.entity.ReviewImage;
import com.example.umc9th.domain.review.repository.ReviewImageRepository;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.user.entity.User;
import com.example.umc9th.domain.user.exception.UserErrorCode;
import com.example.umc9th.domain.user.exception.UserException;
import com.example.umc9th.domain.user.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public ReviewResponseDTO.CreateReviewResultDTO createReview(
        Long userId,
        ReviewRequestDTO.CreateReviewDTO request) {
        // 사용자 조회
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        // 가게 조회
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
            .orElseThrow(() -> new RestaurantException(RestaurantErrorCode.RESTAURANT_NOT_FOUND));

        // 리뷰 생성
        Review review = Review.builder()
            .user(user)
            .restaurant(restaurant)
            .content(request.getContent())
            .rating(request.getRating().floatValue())
            .build();

        Review savedReview = reviewRepository.save(review);

        // 리뷰 이미지 저장
        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            List<ReviewImage> reviewImages = request.getImageUrls().stream()
                .map(url -> ReviewImage.builder()
                    .review(savedReview)
                    .url(url)
                    .build())
                .toList();

            reviewImageRepository.saveAll(reviewImages);
        }

        // 응답 생성
        return ReviewResponseDTO.CreateReviewResultDTO.builder()
            .reviewId(savedReview.getId())
            .build();
    }

    @Transactional(readOnly = true)
    public List<MyReviewResponseDTO> getMyReviews(
        Long userId,
        String restaurantName,
        Integer rating,
        String filterType) {
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

    @Transactional(readOnly = true)
    public ReviewPreViewListDTO getMyReviewList(Long userId, Integer page) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Page<Review> reviewPage = reviewRepository.findAllByUser(user, PageRequest.of(page - 1, 10));
        return com.example.umc9th.domain.review.converter.ReviewConverter.toReviewPreViewListDTO(reviewPage);
    }
}
