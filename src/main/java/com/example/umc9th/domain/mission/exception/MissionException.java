package com.example.umc9th.domain.mission.exception;

import com.example.umc9th.global.apipayload.code.BaseErrorCode;
import com.example.umc9th.global.apipayload.exception.GeneralException;

public class MissionException extends GeneralException {

    public MissionException(BaseErrorCode code) {
        super(code);
    }
}