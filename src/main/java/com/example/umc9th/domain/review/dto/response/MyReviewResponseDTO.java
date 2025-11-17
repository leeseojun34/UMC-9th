package com.example.umc9th.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyReviewResponseDTO {
    
    private Long reviewId;
    private String restaurantName;
    private String content;
    private Float rating;
    private LocalDateTime createdAt;
}
