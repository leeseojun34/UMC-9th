package com.example.umc9th.domain.mission.dto.response;

import com.example.umc9th.domain.mission.enums.MissionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "미션 목록 응답 DTO")
public class MissionListResponseDTO {
    
    @Schema(description = "미션 ID", example = "1")
    private Long missionId;
    
    @Schema(description = "미션 제목", example = "첫 리뷰 작성하기")
    private String title;
    
    @Schema(description = "미션 요구사항", example = "가게에서 식사 후 리뷰를 작성해주세요")
    private String requirement;
    
    @Schema(description = "미션 보상 포인트", example = "500")
    private Integer reward;
    
    @Schema(description = "미션 상태", example = "ACTIVE")
    private MissionStatus status;
    
    @Schema(description = "미션 생성일시", example = "2025-11-25T12:00:00")
    private LocalDateTime createdAt;
}
