package com.example.umc9th.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class ReviewRequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "리뷰 작성 요청 DTO")
    public static class CreateReviewDTO {

        @NotNull(message = "가게 ID는 필수입니다.")
        @Schema(description = "가게 ID", example = "45")
        private Long restaurantId;

        @NotNull(message = "별점은 필수입니다.")
        @Min(value = 1, message = "별점은 1 이상이어야 합니다.")
        @Max(value = 5, message = "별점은 5 이하여야 합니다.")
        @Schema(description = "별점 (1~5)", example = "4")
        private Integer rating;

        @NotBlank(message = "리뷰 내용은 필수입니다.")
        @Schema(description = "리뷰 내용", example = "음 너무 맛있어요")
        private String content;

        @Schema(description = "리뷰 이미지 URL 목록")
        private List<String> imageUrls;
    }
}