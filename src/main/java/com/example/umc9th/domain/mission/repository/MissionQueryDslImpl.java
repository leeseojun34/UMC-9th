package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.dto.response.MissionListResponseDTO;
import com.example.umc9th.domain.mission.entity.QMission;
import com.example.umc9th.domain.restaurant.entity.QRestaurant;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MissionQueryDslImpl implements MissionQueryDsl {

    private final EntityManager em;

    @Override
    public Page<MissionListResponseDTO> findMissionsByRestaurantId(Long restaurantId, Integer page) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QMission mission = QMission.mission;
        QRestaurant restaurant = QRestaurant.restaurant;

        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        List<MissionListResponseDTO> content = queryFactory
            .select(Projections.constructor(
                MissionListResponseDTO.class,
                mission.id,
                mission.title,
                mission.requirement,
                mission.reward,
                mission.status,
                mission.createdAt
            ))
            .from(mission)
            .leftJoin(restaurant).on(restaurant.id.eq(mission.restaurant.id))
            .where(mission.restaurant.id.eq(restaurantId))
            .orderBy(mission.createdAt.desc())
            .offset(offset)
            .limit(pageSize)
            .fetch();

        Long total = queryFactory
            .select(mission.count())
            .from(mission)
            .where(mission.restaurant.id.eq(restaurantId))
            .fetchOne();

        return new PageImpl<>(content, PageRequest.of(page - 1, pageSize), total != null ? total : 0);
    }
}
