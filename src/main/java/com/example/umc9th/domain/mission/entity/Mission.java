package com.example.umc9th.domain.mission.entity;

import com.example.umc9th.domain.mission.enums.MissionStatus;
import com.example.umc9th.domain.restaurant.entity.Restaurant;
import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mission")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String requirement;

    @Enumerated(EnumType.STRING)
    @Column
    private MissionStatus status;

    private Integer reward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<UserMission> userMissions = new HashSet<>();
}
