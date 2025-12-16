package com.example.umc9th.domain.user.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class UserResponseDTO {

    public record JoinResultDTO(
        Long userId,
        LocalDateTime createdAt) {
    }

    @Builder
    public record LoginDTO(
        Long userId,
        String accessToken) {
    }
}
