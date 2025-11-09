package com.example.umc9th.domain.restaurant.controller;

import com.example.umc9th.domain.restaurant.dto.RestaurantSearchResponseDTO;
import com.example.umc9th.domain.restaurant.service.RestaurantService;
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

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurant", description = "가게 API")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/search")
    @Operation(
        summary = "가게 검색",
        description = "지역, 이름으로 가게를 검색하고 정렬 및 페이징"
    )
    public ApiResponse<RestaurantSearchResponseDTO.PageResponse> searchRestaurants(
        @Parameter
        @RequestParam(required = false) String region,

        @Parameter
        @RequestParam(required = false) String keyword,

        @Parameter(description = "정렬 기준: latest(최신순), name(이름순)")
        @RequestParam(defaultValue = "latest") String sortBy,

        @Parameter
        @RequestParam(defaultValue = "0") Integer page,

        @Parameter
        @RequestParam(defaultValue = "10") Integer size
    ) {
        RestaurantSearchResponseDTO.PageResponse result = restaurantService.searchRestaurants(
            region, keyword, sortBy, page, size
        );

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }
}