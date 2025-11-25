package com.example.umc9th.domain.restaurant.exception;

import com.example.umc9th.global.apipayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RestaurantErrorCode implements BaseErrorCode {

    RESTAURANT_NOT_FOUND(
        HttpStatus.NOT_FOUND,
        "RESTAURANT404_1",
        "가게를 찾을 수 없습니다."
    ),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}