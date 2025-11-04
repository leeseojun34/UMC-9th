package com.example.umc9th.domain.restaurant.repository;

import com.example.umc9th.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantQueryDsl {
}
