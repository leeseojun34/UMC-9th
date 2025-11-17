package com.example.umc9th.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "리뷰 작성 응답 DTO")
    public static class CreateReviewResultDTO {

        @Schema(description = "생성된 리뷰 ID", example = "1001")
        private Long reviewId;
    }
}