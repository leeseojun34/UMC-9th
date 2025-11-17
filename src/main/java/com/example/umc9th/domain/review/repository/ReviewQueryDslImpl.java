package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.restaurant.entity.QRestaurant;
import com.example.umc9th.domain.review.dto.response.MyReviewResponseDTO;
import com.example.umc9th.domain.review.entity.QReview;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewQueryDslImpl implements ReviewQueryDsl {

    private final EntityManager em;

    @Override
    public List<MyReviewResponseDTO> findMyReviews(Long userId, Predicate predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QReview review = QReview.review;
        QRestaurant restaurant = QRestaurant.restaurant;

        return queryFactory
            .select(Projections.constructor(
                MyReviewResponseDTO.class,
                review.id,
                restaurant.name,
                review.content,
                review.rating,
                review.createdAt
            ))
            .from(review)
            .leftJoin(restaurant).on(restaurant.id.eq(review.restaurant.id))
            .where(
                review.user.id.eq(userId),
                predicate
            )
            .orderBy(review.createdAt.desc())
            .fetch();
    }
}
