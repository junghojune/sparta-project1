package com.sparta.project.delivery.review.dto;

import com.sparta.project.delivery.review.entity.Review;
import com.sparta.project.delivery.review.entity.ReviewReport;
import com.sparta.project.delivery.user.User;

import java.time.LocalDateTime;

public record ReviewReportDto(
        String reviewReportId,
        User user,
        String reviewId,
        String reportMessage,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {
    public static ReviewReportDto of(User user, String reviewId, String reportMessage) {
        return new ReviewReportDto(
                null, user, reviewId, reportMessage, null,
                null, null, null, null, null, null, null
        );
    }

    public ReviewReport toEntity(User user, Review review) {
        return ReviewReport.of(user, review, reportMessage);
    }
}
