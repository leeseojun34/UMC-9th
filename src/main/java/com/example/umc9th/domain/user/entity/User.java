package com.example.umc9th.domain.user.entity;

import com.example.umc9th.domain.food.entity.UserFood;
import com.example.umc9th.domain.inquiry.entity.Inquiry;
import com.example.umc9th.domain.mission.entity.UserMission;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.user.enums.Gender;
import com.example.umc9th.domain.user.enums.Provider;
import com.example.umc9th.domain.user.enums.UserStatus;
import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String nickname;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    private LocalDate birth;

    @Column
    private String phone;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "total_point")
    private Integer totalPoint;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    @Column
    private Provider provider;

    @Enumerated(EnumType.STRING)
    @Column
    private UserStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Inquiry> inquiries = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<UserTerm> userTerms = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<UserFood> userFoods = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<UserMission> userMissions = new HashSet<>();
}
