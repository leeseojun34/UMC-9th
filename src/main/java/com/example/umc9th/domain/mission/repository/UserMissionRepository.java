package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.entity.UserMission;
import com.example.umc9th.domain.mission.enums.MissionStatus;
import com.example.umc9th.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMissionRepository extends JpaRepository<UserMission, Long>, UserMissionQueryDsl {

    Page<UserMission> findAllByUserAndStatus(User user, MissionStatus status, Pageable pageable);

    Optional<UserMission> findByMissionIdAndUserIdAndStatus(Long missionId, Long userId, MissionStatus status);
}
