package com.example.umc9th.domain.review.controller;

import com.example.umc9th.domain.review.dto.MyReviewResponseDTO;
import com.example.umc9th.domain.review.service.ReviewService;
import com.example.umc9th.global.apipayload.ApiResponse;
import com.example.umc9th.global.apipayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/my")
    @Operation(summary = "내가 작성한 리뷰 조회", description = "사용자가 작성한 리뷰를 가게별, 별점별로 필터링하여 조회")
    public ApiResponse<List<MyReviewResponseDTO>> getMyReviews(
        @Parameter(required = true)
        @RequestParam Long userId,

        @RequestParam(required = false) String restaurantName,

        @RequestParam(required = false) Integer rating,

        @Parameter(description = "필터 타입: restaurant, rating, both, none")
        @RequestParam(defaultValue = "none") String filterType
    ) {
        List<MyReviewResponseDTO> reviews = reviewService.getMyReviews(userId, restaurantName, rating, filterType);

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, reviews);
    }
}
