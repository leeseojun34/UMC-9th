package com.example.umc9th.domain.user.dto;

import java.time.LocalDateTime;

public class UserResponseDTO {

    public record JoinResultDTO(
        Long userId,
        LocalDateTime createdAt
    ) {
    }
}
