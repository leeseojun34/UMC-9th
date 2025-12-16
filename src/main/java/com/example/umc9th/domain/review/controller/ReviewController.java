package com.example.umc9th.domain.review.controller;

import com.example.umc9th.domain.review.dto.request.ReviewRequestDTO;
import com.example.umc9th.domain.review.dto.response.MyReviewResponseDTO;
import com.example.umc9th.domain.review.dto.response.ReviewPreViewListDTO;
import com.example.umc9th.domain.review.dto.response.ReviewResponseDTO;
import com.example.umc9th.domain.review.service.ReviewService;
import com.example.umc9th.global.apipayload.ApiResponse;
import com.example.umc9th.global.apipayload.code.GeneralSuccessCode;
import com.example.umc9th.global.validation.annotation.CheckPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewiew")
@RequiredArgsConstructor
@Validated
@Tag(name = "리뷰", description = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "리뷰 작성", description = "가게에 대한 리뷰를 작성합니다.")
    public ApiResponse<ReviewResponseDTO.CreateReviewResultDTO> createReview(
        @Parameter(description = "사용자 ID", required = true) @RequestParam Long userId,
        @Valid @RequestBody ReviewRequestDTO.CreateReviewDTO request) {
        ReviewResponseDTO.CreateReviewResultDTO result = reviewService.createReview(userId, request);

        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, result);
    }

    @GetMapping("/my")
    @Operation(summary = "내가 작성한 리뷰 조회", description = "사용자가 작성한 리뷰를 가게별, 별점별로 필터링하여 조회")
    public ApiResponse<List<MyReviewResponseDTO>> getMyReviews(
        @Parameter(required = true) @RequestParam Long userId,
        @RequestParam(required = false) String restaurantName,
        @RequestParam(required = false) Integer rating,
        @Parameter(description = "필터 타입: restaurant, rating, both, none") @RequestParam(defaultValue = "none") String filterType) {
        List<MyReviewResponseDTO> reviews = reviewService.getMyReviews(userId, restaurantName, rating, filterType);

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, reviews);
    }

    @GetMapping("/my/paging")
    @Operation(summary = "내가 작성한 리뷰 목록 조회 (페이징)", description = "사용자가 작성한 리뷰를 페이징하여 조회합니다.")
    public ApiResponse<ReviewPreViewListDTO> getMyReviewsPaging(
        @Parameter(description = "사용자 ID", required = true) @RequestParam Long userId,
        @Parameter(description = "페이지 번호", required = true) @CheckPage @RequestParam(defaultValue = "1") Integer page) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, reviewService.getMyReviewList(userId, page));
    }
}
