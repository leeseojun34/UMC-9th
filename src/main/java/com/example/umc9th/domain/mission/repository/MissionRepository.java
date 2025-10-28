package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("SELECT m FROM Mission m " +
        "JOIN FETCH m.restaurant r " +
        "JOIN r.region rg " +
        "LEFT JOIN m.userMissions um WITH um.user = :user " +
        "WHERE rg.name = :regionName AND um.id IS NULL AND m.id < :cursorId " +
        "ORDER BY m.id DESC")
    Page<Mission> findAvailableMissions(
        @Param("user") User user,
        @Param("regionName") String regionName,
        @Param("cursorId") Long cursorId,
        Pageable pageable
    );
}
