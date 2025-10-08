package com.example.umc9th.domain.region.entity;

import com.example.umc9th.domain.restaurant.entity.Restaurant;
import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "region")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Region extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "region", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Restaurant> restaurants = new HashSet<>();
}
