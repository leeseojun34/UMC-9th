package com.example.umc9th.domain.user.exception;

import com.example.umc9th.global.apipayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements BaseErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,
        "USER404",
        "사용자를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
