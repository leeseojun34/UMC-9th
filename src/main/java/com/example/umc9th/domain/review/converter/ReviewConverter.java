package com.example.umc9th.domain.review.converter;

import com.example.umc9th.domain.review.dto.response.MyReviewResponseDTO;
import com.example.umc9th.domain.review.dto.response.ReviewPreViewListDTO;
import com.example.umc9th.domain.review.entity.Review;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    public static MyReviewResponseDTO toMyReviewResponseDTO(Review review) {
        return MyReviewResponseDTO.builder()
            .reviewId(review.getId())
            .restaurantName(review.getRestaurant().getName())
            .content(review.getContent())
            .rating(review.getRating())
            .createdAt(review.getCreatedAt())
            .build();
    }

    public static ReviewPreViewListDTO toReviewPreViewListDTO(Page<Review> reviewPage) {
        List<MyReviewResponseDTO> myReviewResponseDTOList = reviewPage.stream()
            .map(ReviewConverter::toMyReviewResponseDTO)
            .collect(Collectors.toList());

        return ReviewPreViewListDTO.builder()
            .reviewList(myReviewResponseDTOList)
            .listSize(myReviewResponseDTOList.size())
            .totalPage(reviewPage.getTotalPages())
            .totalElements(reviewPage.getTotalElements())
            .isFirst(reviewPage.isFirst())
            .isLast(reviewPage.isLast())
            .build();
    }
}
