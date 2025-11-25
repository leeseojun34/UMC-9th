package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.dto.response.MyReviewResponseDTO;
import com.querydsl.core.BooleanBuilder;

public interface ReviewQueryDsl {

    java.util.List<MyReviewResponseDTO> findMyReviews(Long userId, BooleanBuilder builder);
}
