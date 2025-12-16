package com.example.umc9th.global.common.code;

import com.example.umc9th.global.apipayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PageErrorCode implements BaseErrorCode {

    INVALID_PAGE(HttpStatus.BAD_REQUEST,
        "PAGE400_1",
        "페이지 번호는 1 이상이어야 합니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}