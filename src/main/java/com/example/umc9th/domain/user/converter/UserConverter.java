package com.example.umc9th.domain.user.converter;

import com.example.umc9th.domain.user.dto.UserRequestDTO;
import com.example.umc9th.domain.user.dto.UserResponseDTO;
import com.example.umc9th.domain.user.entity.User;
import com.example.umc9th.domain.user.enums.Role;

import java.util.HashSet;

public class UserConverter {

    public static User toUser(UserRequestDTO.JoinDTO dto, String encodedPassword, Role role) {
        return User.builder()
            .name(dto.name())
            .email(dto.email())
            .password(encodedPassword)
            .role(role)
            .birth(dto.birth())
            .address(dto.address())
            .gender(dto.gender())
            .userFoods(new HashSet<>())
            .build();
    }

    public static UserResponseDTO.JoinResultDTO toJoinResultDTO(User user) {
        return new UserResponseDTO.JoinResultDTO(user.getId(), user.getCreatedAt());
    }
}
