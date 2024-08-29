package com.sparta.project.delivery.review.dto;

import com.sparta.project.delivery.user.User;

public record ReviewRequest(
        String orderId,
        int rating,
        String comment
) {
    public ReviewDto toDto(User user) {
        return ReviewDto.of(
                user, orderId, rating, comment
        );
    }
}
