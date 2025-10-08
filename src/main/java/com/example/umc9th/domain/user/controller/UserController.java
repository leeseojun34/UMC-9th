package com.example.umc9th.domain.user.controller;

import com.example.umc9th.domain.user.service.UserService;
import com.example.umc9th.global.apipayload.ApiResponse;
import com.example.umc9th.global.apipayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "User API", description = "사용자 관련 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
        summary = "회원 탈퇴",
        description = "회원과 관련된 모든 데이터를 삭제합니다. " +
                     "리뷰, 문의, 약관 동의, 음식 선호도, 미션 등 모든 연관 데이터가 함께 삭제됩니다."
    )
    @DeleteMapping("/{userId}")
    public ApiResponse<String> withdrawUser(
            @Parameter(description = "삭제할 사용자 ID", required = true)
            @PathVariable Long userId
    ) {
        log.info("회원 탈퇴 요청: userId = {}", userId);
        
        userService.withdrawUser(userId);
        
        return ApiResponse.onSuccess(
            GeneralSuccessCode.OK,
            "회원 탈퇴가 완료되었습니다."
        );
    }
}
