package com.example.umc9th.domain.user.exception;

import com.example.umc9th.global.apipayload.exception.GeneralException;

public class UserException extends GeneralException {

    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
