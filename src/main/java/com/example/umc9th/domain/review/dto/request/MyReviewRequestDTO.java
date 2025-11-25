package com.example.umc9th.domain.review.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyReviewRequestDTO {

    private String restaurantName;
    private Integer rating;
    // "restaurant", "star", "both", "none"
    private String filterType;
}
