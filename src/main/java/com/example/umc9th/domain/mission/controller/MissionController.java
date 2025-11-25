package com.example.umc9th.domain.mission.controller;

import com.example.umc9th.domain.mission.dto.MissionRequestDTO;
import com.example.umc9th.domain.mission.dto.MissionResponseDTO;
import com.example.umc9th.domain.mission.service.MissionService;
import com.example.umc9th.global.apipayload.ApiResponse;
import com.example.umc9th.global.apipayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mission")
@RequiredArgsConstructor
@Tag(name = "Mission", description = "미션 API")
public class MissionController {

    private final MissionService missionService;

    @PostMapping
    @Operation(summary = "미션 도전", description = "가게의 미션에 도전합니다.")
    public ApiResponse<MissionResponseDTO.ChallengeMissionResultDTO> challengeMission(
        @Parameter(description = "사용자 ID (임시)", required = true)
        @RequestParam Long userId,

        @Valid @RequestBody MissionRequestDTO.ChallengeMissionDTO request
    ) {
        MissionResponseDTO.ChallengeMissionResultDTO result =
            missionService.challengeMission(userId, request);

        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, result);
    }
}