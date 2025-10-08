package com.example.umc9th.domain.user.entity;

import com.example.umc9th.domain.user.enums.TermType;
import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "term")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Term extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column
    private TermType name;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_required")
    private Boolean isRequired;

    @OneToMany(mappedBy = "term", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<UserTerm> userTerms = new HashSet<>();
}
