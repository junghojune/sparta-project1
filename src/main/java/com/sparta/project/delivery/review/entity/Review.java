package com.sparta.project.delivery.review.entity;

import com.sparta.project.delivery.common.BaseEntity;
import com.sparta.project.delivery.order.Order;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "review_id", updatable = false, nullable = false)
    private String reviewId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Setter
    @Column(nullable = false)
    private int rating;

    @Setter
    @Column(length = 500, nullable = false)
    private String comment;

    @Setter
    @Column(name = "report_flag", nullable = false)
    @Builder.Default
    private boolean reportFlag = false;

    @Setter
    @Column(name = "report_message")
    private String reportMessage;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(reviewId, review.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reviewId);
    }
}
