package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.entity.UserMission;
import com.example.umc9th.domain.mission.enums.MissionStatus;
import com.example.umc9th.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMissionRepository extends JpaRepository<UserMission,Long> {

    @Query("SELECT um FROM UserMission um " +
        "JOIN FETCH um.mission m " +
        "JOIN FETCH m.restaurant r " +
        "WHERE um.user = :user AND um.status IN :statuses AND um.id < :cursorId " +
        "ORDER BY um.id DESC")
    Page<UserMission> findMyMissions(
        @Param("user") User user,
        @Param("statuses") List<MissionStatus> statuses,
        @Param("cursorId") Long cursorId,
        Pageable pageable
    );

}
