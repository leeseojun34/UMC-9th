package com.example.umc9th.domain.mission.controller;

import com.example.umc9th.domain.mission.dto.MissionRequestDTO;
import com.example.umc9th.domain.mission.dto.MissionResponseDTO;
import com.example.umc9th.domain.mission.dto.response.MissionPreViewListDTO;
import com.example.umc9th.domain.mission.dto.response.UserMissionListResponseDTO;
import com.example.umc9th.domain.mission.dto.response.UserMissionPreViewListDTO;
import com.example.umc9th.domain.mission.service.MissionService;
import com.example.umc9th.global.apipayload.ApiResponse;
import com.example.umc9th.global.apipayload.code.GeneralSuccessCode;
import com.example.umc9th.global.validation.annotation.CheckPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mission")
@RequiredArgsConstructor
@Validated
@Tag(name = "Mission", description = "미션 API")
public class MissionController {

    private final MissionService missionService;

    @PostMapping
    @Operation(summary = "미션 도전", description = "가게의 미션에 도전합니다.")
    public ApiResponse<MissionResponseDTO.ChallengeMissionResultDTO> challengeMission(
        @Parameter(description = "사용자 ID", required = true) @RequestParam Long userId,
        @Valid @RequestBody MissionRequestDTO.ChallengeMissionDTO request) {
        MissionResponseDTO.ChallengeMissionResultDTO result = missionService.challengeMission(userId, request);

        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, result);
    }

    @GetMapping("/restaurant/{restaurantId}")
    @Operation(summary = "특정 가게의 미션 목록 조회 (페이징)", description = "특정 가게의 미션 목록을 페이징하여 조회합니다.")
    public ApiResponse<MissionPreViewListDTO> getMissionsByRestaurant(
        @Parameter(description = "가게 ID", required = true) @PathVariable Long restaurantId,
        @Parameter(description = "페이지 번호", required = true) @CheckPage @RequestParam(defaultValue = "1") Integer page) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, missionService.getStoreMissionList(restaurantId, page));
    }

    @GetMapping("/my/in-progress")
    @Operation(summary = "내가 진행중인 미션 목록 조회 (페이징)", description = "사용자가 진행중인 미션 목록을 페이징하여 조회합니다.")
    public ApiResponse<UserMissionPreViewListDTO> getMyInProgressMissions(
        @Parameter(description = "사용자 ID", required = true) @RequestParam Long userId,
        @Parameter(description = "페이지 번호", required = true) @CheckPage @RequestParam(defaultValue = "1") Integer page) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, missionService.getMyOngoingMissionList(userId, page));
    }

    @PatchMapping("/{missionId}/complete")
    @Operation(summary = "진행중인 미션 완료로 변경", description = "진행중인 미션을 완료 상태로 변경하고, 변경된 미션 정보를 반환합니다.")
    public ApiResponse<UserMissionListResponseDTO> completeMission(
        @Parameter(description = "사용자 ID", required = true) @RequestParam Long userId,
        @Parameter(description = "미션 ID", required = true) @PathVariable Long missionId) {
        UserMissionListResponseDTO result = missionService.completeMission(userId, missionId);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }
}
