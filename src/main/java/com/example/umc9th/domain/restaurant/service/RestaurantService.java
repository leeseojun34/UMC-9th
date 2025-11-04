package com.example.umc9th.domain.restaurant.service;

import com.example.umc9th.domain.restaurant.dto.RestaurantSearchResponseDTO;
import com.example.umc9th.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantSearchResponseDTO.PageResponse searchRestaurants(
        String regionName,
        String keyword,
        String sortBy,
        Integer page,
        Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<RestaurantSearchResponseDTO> resultPage = restaurantRepository.searchRestaurants(regionName, keyword, sortBy, pageable);

        return RestaurantSearchResponseDTO.PageResponse.of(resultPage);
    }
}