package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.dto.response.MissionListResponseDTO;
import org.springframework.data.domain.Page;

public interface MissionQueryDsl {

    Page<MissionListResponseDTO> findMissionsByRestaurantId(Long restaurantId, Integer page);
}
