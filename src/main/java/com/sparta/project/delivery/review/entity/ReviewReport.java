package com.sparta.project.delivery.review.entity;

import com.sparta.project.delivery.common.BaseEntity;
import com.sparta.project.delivery.user.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_review_report")
public class ReviewReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "review_report_id", updatable = false, nullable = false)
    private String reviewReportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User reporter;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false, updatable = false)
    private Review review;

    private String reportMessage;

    public static ReviewReport of(User user, Review review, String reportMessage) {
        return ReviewReport.builder()
                .reporter(user)
                .review(review)
                .reportMessage(reportMessage)
                .build();
    }
}
