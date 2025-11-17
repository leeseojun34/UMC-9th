package com.example.umc9th.domain.review.exception;

import com.example.umc9th.global.apipayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
    
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, 
        "REVIEW404_1", 
        "리뷰를 찾을 수 없습니다."),
    INVALID_RATING(HttpStatus.BAD_REQUEST,
        "REVIEW400_1",
        "별점은 1에서 5 사이의 값이어야 합니다."),
    ;
    
    private final HttpStatus status;
    private final String code;
    private final String message;
}