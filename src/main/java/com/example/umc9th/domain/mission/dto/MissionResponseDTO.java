package com.example.umc9th.domain.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MissionResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "미션 도전 응답 DTO")
    public static class ChallengeMissionResultDTO {

        @Schema(description = "생성된 사용자-미션 ID", example = "1")
        private Long userMissionId;
    }
}