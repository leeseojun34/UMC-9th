package com.example.umc9th.domain.review.exception;

import com.example.umc9th.global.apipayload.code.BaseErrorCode;
import com.example.umc9th.global.apipayload.exception.GeneralException;

public class ReviewException extends GeneralException {
    
    public ReviewException(BaseErrorCode code) {
        super(code);
    }
}