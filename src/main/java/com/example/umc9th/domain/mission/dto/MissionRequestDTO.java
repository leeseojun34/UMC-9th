package com.example.umc9th.domain.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MissionRequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "미션 도전 요청 DTO")
    public static class ChallengeMissionDTO {

        @NotNull(message = "가게 ID는 필수입니다.")
        @Schema(description = "가게 ID", example = "1")
        private Long restaurantId;

        @NotNull(message = "미션 ID는 필수입니다.")
        @Schema(description = "미션 ID", example = "1")
        private Long missionId;
    }
}