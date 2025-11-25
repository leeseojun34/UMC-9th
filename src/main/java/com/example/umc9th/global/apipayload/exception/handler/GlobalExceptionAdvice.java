package com.example.umc9th.global.apipayload.exception.handler;

import com.example.umc9th.global.apipayload.ApiResponse;
import com.example.umc9th.global.apipayload.code.BaseErrorCode;
import com.example.umc9th.global.apipayload.code.GeneralErrorCode;
import com.example.umc9th.global.apipayload.exception.GeneralException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomException(GeneralException ex) {
        log.warn("[ CustomException ]: {}", ex.getCode().getMessage());
        // 커스텀 예외에 정의된 에러 코드와 메시지를 포함한 응답 제공
        // onFailure 메서드에 null을 전달하여 데이터 부분이 없음을 명시
        ApiResponse<Object> errorResponse = ApiResponse.onFailure(ex.getCode(), null);
        return ResponseEntity
            .status(ex.getCode().getStatus())
            .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex
    ) {
        log.warn("[ MethodArgumentNotValidException ]: Validation failed");

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
            .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        BaseErrorCode errorCode = GeneralErrorCode.BAD_REQUEST_400;
        ApiResponse<Map<String, String>> errorResponse = ApiResponse.onFailure(errorCode, errors);

        return ResponseEntity
            .status(errorCode.getStatus())
            .body(errorResponse);
    }

    // 그 외의 정의되지 않은 모든 예외 처리
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResponse<Object>> handleAllException(
        Exception ex
    ) {
        log.error("[WARNING] Internal Server Error : {} ", ex.getMessage());
        BaseErrorCode errorCode = GeneralErrorCode.INTERNAL_SERVER_ERROR_500;
        // onFailure 메서드에 null을 전달하여 데이터 부분이 없음을 명시
        ApiResponse<Object> errorResponse = ApiResponse.onFailure(errorCode, null);
        return ResponseEntity
            .status(errorCode.getStatus())
            .body(errorResponse);
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(
        ConstraintViolationException ex
    ) {
        log.warn("[ ConstraintViolationException ]: {}", ex.getMessage());
        return ResponseEntity
            .status(GeneralErrorCode.BAD_REQUEST_400.getStatus())
            .body(ApiResponse.onFailure(GeneralErrorCode.BAD_REQUEST_400, ex.getMessage()));
    }
}