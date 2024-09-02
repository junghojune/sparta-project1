package com.sparta.project.delivery.review.dto;

import java.time.LocalDateTime;

public record UserAllReviewResponse(
        String reviewId,
        String storeId,
        String storeName,
        String orderId,
        String userName,
        int rating,
        String comment,
        LocalDateTime reviewDate
) {
    public static UserAllReviewResponse from(ReviewDto dto) {
        return new UserAllReviewResponse(
                dto.reviewId(),
                dto.orderId(),
                dto.store().storeId(),
                dto.store().name(),
                dto.userDto().username(),
                dto.rating(),
                dto.comment(),
                dto.createdAt()
        );
    }
}
