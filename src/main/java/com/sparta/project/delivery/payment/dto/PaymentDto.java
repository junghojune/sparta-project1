package com.sparta.project.delivery.payment.dto;

import com.sparta.project.delivery.order.entity.Order;
import com.sparta.project.delivery.payment.entity.Payment;
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.dto.UserDto;

import java.time.LocalDateTime;

public record PaymentDto(
        String paymentId,
        UserDto userDto,
        String orderId,
        String paymentInfo,
        Long amount,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {
    public static PaymentDto of(UserDto userDto, String orderId, String paymentInfo, Long amount){
        return new PaymentDto(
                null, userDto, orderId, paymentInfo, amount, null, null,
                null, null, null, null, null, null
        );
    }

    public static PaymentDto from(Payment entity) {
        return new PaymentDto(
                entity.getPaymentId(),
                UserDto.from(entity.getUser()),
                entity.getOrder().getOrderId(),
                entity.getPaymentInfo(),
                entity.getAmount(),
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

    public Payment toEntity(User user, Order order) {
        return Payment.builder()
                .user(user)
                .order(order)
                .paymentInfo(paymentInfo)
                .amount(amount)
                .build();
    }
}
