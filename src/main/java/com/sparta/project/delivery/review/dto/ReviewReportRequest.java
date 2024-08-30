package com.sparta.project.delivery.review.dto;

import com.sparta.project.delivery.user.dto.UserDto;

public record ReviewReportRequest(
        String reportMessage
) {
    public ReviewReportDto toDto(UserDto userDto, String reviewId) {
        return ReviewReportDto.of(userDto, reviewId, reportMessage);
    }
}
