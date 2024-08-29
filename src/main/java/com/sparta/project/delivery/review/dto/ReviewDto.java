package com.sparta.project.delivery.review.dto;

import com.sparta.project.delivery.order.dto.OrderDto;
import com.sparta.project.delivery.order.entity.Order;
import com.sparta.project.delivery.review.entity.Review;
import com.sparta.project.delivery.store.dto.StoreDto;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.user.User;

import java.time.LocalDateTime;

public record ReviewDto(
        String reviewId,
        String orderId,
        User user,
        StoreDto store,
        int rating,
        String comment,
        Boolean reportFlag,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {
    public static ReviewDto of(User user, String orderId, int rating, String comment) {
        return new ReviewDto(
                null, orderId, user, null, rating, comment, null,
                null, null, null, null, null,
                null, null, null
        );
    }

    public static ReviewDto from(Review entity) {
        return new ReviewDto(
                entity.getReviewId(),
                OrderDto.from(entity.getOrder()).orderId(),
                entity.getUser(),
                StoreDto.from(entity.getStore()),
                entity.getRating(),
                entity.getComment(),
                entity.isReportFlag(),
                entity.getIsPublic(),
                entity.getIsDeleted(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy(),
                entity.getDeletedAt(),
                entity.getDeletedBy()
        );
    }

    public Review toEntity(User user, Order order, Store store) {
        return Review.of(user, order, store, rating, comment);
    }
}
