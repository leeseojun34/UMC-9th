package com.example.umc9th.domain.food.entity;

import com.example.umc9th.domain.food.enums.FoodCategory;
import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "food")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Food extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FoodCategory name;

    @OneToMany(mappedBy = "food", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<UserFood> userFoods = new HashSet<>();
}
