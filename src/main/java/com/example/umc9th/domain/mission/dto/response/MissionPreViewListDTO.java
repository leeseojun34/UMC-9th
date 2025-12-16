package com.example.umc9th.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "미션 목록 조회 응답 DTO")
public class MissionPreViewListDTO {
    @Schema(description = "미션 목록")
    private List<MissionListResponseDTO> missionList;
    @Schema(description = "리스트 크기")
    private Integer listSize;
    @Schema(description = "전체 페이지 수")
    private Integer totalPage;
    @Schema(description = "전체 요소 수")
    private Long totalElements;
    @Schema(description = "첫 페이지 여부")
    private Boolean isFirst;
    @Schema(description = "마지막 페이지 여부")
    private Boolean isLast;
}
