package com.example.umc9th.domain.mission.exception;

import com.example.umc9th.global.apipayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND,
        "MISSION404_1",
        "미션을 찾을 수 없습니다."),
    MISSION_ALREADY_CHALLENGED(HttpStatus.BAD_REQUEST,
        "MISSION400_1",
        "이미 도전 중인 미션입니다."),
    MISSION_NOT_BELONG_TO_RESTAURANT(HttpStatus.BAD_REQUEST,
        "MISSION400_2",
        "해당 미션은 이 가게의 미션이 아닙니다."),
    USER_MISSION_NOT_FOUND(HttpStatus.NOT_FOUND,
        "MISSION404_2",
        "사용자 미션을 찾을 수 없습니다."),
    MISSION_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST,
        "MISSION400_3",
        "이미 완료된 미션입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
