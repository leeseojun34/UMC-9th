package com.example.umc9th.domain.user.dto;

import com.example.umc9th.domain.user.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class UserRequestDTO {

    public record JoinDTO(
        @NotBlank String name,
        @Email String email,
        @NotBlank String password,
        @NotNull Gender gender,
        @NotNull LocalDate birth,
        @NotBlank String address,
        List<Long> preferCategory) {
    }

    public record LoginDTO(
        @NotBlank String email,
        @NotBlank String password) {
    }
}
