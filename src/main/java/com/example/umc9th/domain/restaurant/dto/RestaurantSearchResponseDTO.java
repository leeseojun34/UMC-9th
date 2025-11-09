package com.example.umc9th.domain.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantSearchResponseDTO {

    private Long restaurantId;
    private String name;
    private String regionName;
    private String address;
    private Float rating;
    private LocalDateTime createdAt;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageResponse {
        private List<RestaurantSearchResponseDTO> items;
        private int pageNo;
        private int numOfRows;
        private long totalCount;
        private int totalPages;

        public static PageResponse of(Page<RestaurantSearchResponseDTO> page) {
            return PageResponse.builder()
                .items(page.getContent())
                .pageNo(page.getNumber())
                .numOfRows(page.getSize())
                .totalCount(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
        }
    }
}