package com.sparta.project.delivery.review.dto;

import com.sparta.project.delivery.user.User;

public record ReviewReportRequest(
        String reportMessage
) {
    public ReviewReportDto toDto(User user, String reviewId) {
        return ReviewReportDto.of(user, reviewId, reportMessage);
    }
}
