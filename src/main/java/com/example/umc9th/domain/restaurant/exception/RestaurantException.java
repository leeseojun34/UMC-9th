package com.example.umc9th.domain.restaurant.exception;

import com.example.umc9th.global.apipayload.code.BaseErrorCode;
import com.example.umc9th.global.apipayload.exception.GeneralException;

public class RestaurantException extends GeneralException {

    public RestaurantException(BaseErrorCode code) {
        super(code);
    }
}