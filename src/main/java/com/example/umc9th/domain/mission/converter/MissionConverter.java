package com.example.umc9th.domain.mission.converter;

import com.example.umc9th.domain.mission.dto.response.MissionListResponseDTO;
import com.example.umc9th.domain.mission.dto.response.MissionPreViewListDTO;
import com.example.umc9th.domain.mission.dto.response.UserMissionListResponseDTO;
import com.example.umc9th.domain.mission.dto.response.UserMissionPreViewListDTO;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.entity.UserMission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    public static MissionListResponseDTO toMissionListResponseDTO(Mission mission) {
        return MissionListResponseDTO.builder()
                .missionId(mission.getId())
                .title(mission.getTitle())
                .requirement(mission.getRequirement())
                .reward(mission.getReward())
                .status(mission.getStatus())
                .createdAt(mission.getCreatedAt())
                .build();
    }

    public static MissionPreViewListDTO toMissionPreViewListDTO(Page<Mission> missionPage) {
        List<MissionListResponseDTO> missionListResponseDTOList = missionPage.stream()
                .map(MissionConverter::toMissionListResponseDTO)
                .collect(Collectors.toList());

        return MissionPreViewListDTO.builder()
                .missionList(missionListResponseDTOList)
                .listSize(missionListResponseDTOList.size())
                .totalPage(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .isFirst(missionPage.isFirst())
                .isLast(missionPage.isLast())
                .build();
    }

    public static UserMissionListResponseDTO toUserMissionListResponseDTO(UserMission userMission) {
        return UserMissionListResponseDTO.builder()
                .userMissionId(userMission.getId())
                .missionId(userMission.getMission().getId())
                .title(userMission.getMission().getTitle())
                .requirement(userMission.getMission().getRequirement())
                .reward(userMission.getMission().getReward())
                .restaurantName(userMission.getMission().getRestaurant().getName())
                .status(userMission.getStatus())
                .createdAt(userMission.getCreatedAt())
                .build();
    }

    public static UserMissionPreViewListDTO toUserMissionPreViewListDTO(Page<UserMission> userMissionPage) {
        List<UserMissionListResponseDTO> userMissionListResponseDTOList = userMissionPage.stream()
                .map(MissionConverter::toUserMissionListResponseDTO)
                .collect(Collectors.toList());

        return UserMissionPreViewListDTO.builder()
                .missionList(userMissionListResponseDTOList)
                .listSize(userMissionListResponseDTOList.size())
                .totalPage(userMissionPage.getTotalPages())
                .totalElements(userMissionPage.getTotalElements())
                .isFirst(userMissionPage.isFirst())
                .isLast(userMissionPage.isLast())
                .build();
    }
}
