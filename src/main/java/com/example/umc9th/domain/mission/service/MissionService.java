package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.mission.converter.MissionConverter;
import com.example.umc9th.domain.mission.dto.MissionRequestDTO;
import com.example.umc9th.domain.mission.dto.MissionResponseDTO;
import com.example.umc9th.domain.mission.dto.response.MissionPreViewListDTO;
import com.example.umc9th.domain.mission.dto.response.UserMissionListResponseDTO;
import com.example.umc9th.domain.mission.dto.response.UserMissionPreViewListDTO;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.entity.UserMission;
import com.example.umc9th.domain.mission.enums.MissionStatus;
import com.example.umc9th.domain.mission.exception.MissionErrorCode;
import com.example.umc9th.domain.mission.exception.MissionException;
import com.example.umc9th.domain.mission.repository.MissionRepository;
import com.example.umc9th.domain.mission.repository.UserMissionRepository;
import com.example.umc9th.domain.restaurant.entity.Restaurant;
import com.example.umc9th.domain.restaurant.exception.RestaurantErrorCode;
import com.example.umc9th.domain.restaurant.exception.RestaurantException;
import com.example.umc9th.domain.restaurant.repository.RestaurantRepository;
import com.example.umc9th.domain.user.entity.User;
import com.example.umc9th.domain.user.exception.UserErrorCode;
import com.example.umc9th.domain.user.exception.UserException;
import com.example.umc9th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public MissionResponseDTO.ChallengeMissionResultDTO challengeMission(
        Long userId,
        MissionRequestDTO.ChallengeMissionDTO request
    ) {
        // 사용자 조회
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        // 가게 조회
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
            .orElseThrow(() -> new RestaurantException(RestaurantErrorCode.RESTAURANT_NOT_FOUND));

        // 미션 조회
        Mission mission = missionRepository.findById(request.getMissionId())
            .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));

        // 미션이 해당 가게의 미션인지 검증
        if (!mission.getRestaurant().getId().equals(restaurant.getId())) {
            throw new MissionException(MissionErrorCode.MISSION_NOT_BELONG_TO_RESTAURANT);
        }

        // 이미 도전 중인 미션인지 확인
        boolean alreadyChallenged = user.getUserMissions().stream()
            .anyMatch(um -> um.getMission().getId().equals(mission.getId())
                && um.getStatus() == MissionStatus.IN_PROGRESS);

        if (alreadyChallenged) {
            throw new MissionException(MissionErrorCode.MISSION_ALREADY_CHALLENGED);
        }

        // UserMission 생성 및 저장
        UserMission userMission = UserMission.builder()
            .user(user)
            .mission(mission)
            .status(MissionStatus.IN_PROGRESS)
            .build();

        UserMission savedUserMission = userMissionRepository.save(userMission);

        return MissionResponseDTO.ChallengeMissionResultDTO.builder()
            .userMissionId(savedUserMission.getId())
            .build();
    }

    @Transactional(readOnly = true)
    public MissionPreViewListDTO getStoreMissionList(
        Long restaurantId,
        Integer page
    ) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new RestaurantException(RestaurantErrorCode.RESTAURANT_NOT_FOUND));

        Page<Mission> missionPage = missionRepository.findAllByRestaurant(restaurant,
            PageRequest.of(page - 1, 10));
        return MissionConverter.toMissionPreViewListDTO(missionPage);
    }

    @Transactional(readOnly = true)
    public UserMissionPreViewListDTO getMyOngoingMissionList(
        Long userId,
        Integer page
    ) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Page<UserMission> userMissionPage = userMissionRepository.findAllByUserAndStatus(user,
            MissionStatus.IN_PROGRESS,
            PageRequest.of(page - 1, 10));
        return MissionConverter.toUserMissionPreViewListDTO(userMissionPage);
    }

    @Transactional
    public UserMissionListResponseDTO completeMission(Long userId, Long missionId) {
        // 사용자 확인
        userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        // 진행중인 미션 조회
        UserMission userMission = userMissionRepository
            .findByMissionIdAndUserIdAndStatus(missionId, userId, MissionStatus.IN_PROGRESS)
            .orElseThrow(() -> new MissionException(MissionErrorCode.USER_MISSION_NOT_FOUND));

        userMission.completeStatus();

        return MissionConverter.toUserMissionListResponseDTO(userMission);
    }
}
