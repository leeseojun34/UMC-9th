package com.example.umc9th.domain.inquiry.entity;

import com.example.umc9th.domain.inquiry.enums.InquiryStatus;
import com.example.umc9th.domain.user.entity.User;
import com.example.umc9th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "inquiry")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Inquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column
    private InquiryStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<InquiryReply> inquiryReplies = new HashSet<>();

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<InquiryImage> inquiryImages = new HashSet<>();
}
