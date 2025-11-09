package com.example.umc9th.domain.restaurant.repository;

import com.example.umc9th.domain.restaurant.dto.RestaurantSearchResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantQueryDsl {
    
    Page<RestaurantSearchResponseDTO> searchRestaurants(
            String regionName,
            String keyword,
            String sortBy,
            Pageable pageable
    );
}
