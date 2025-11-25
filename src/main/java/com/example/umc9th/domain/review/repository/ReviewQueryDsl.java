package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.dto.response.MyReviewResponseDTO;
import com.querydsl.core.types.Predicate;

import java.util.List;

public interface ReviewQueryDsl {

    List<MyReviewResponseDTO> findMyReviews(Long userId, Predicate predicate);
}
