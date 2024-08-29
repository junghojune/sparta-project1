package com.sparta.project.delivery.review.dto;

import java.time.LocalDateTime;

public record ReviewResponse(
        String reviewId,
        String storeId,
        String orderId,
        String userName,
        int rating,
        String comment,
        LocalDateTime reviewDate
) {
    public static ReviewResponse from(ReviewDto dto) {
        return new ReviewResponse(
                dto.reviewId(),
                dto.orderId(),
                dto.store().storeId(),
                dto.user().getName(),
                dto.rating(),
                dto.comment(),
                dto.createdAt()
        );
    }
}
