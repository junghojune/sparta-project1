package com.sparta.project.delivery.payment.dto;

import java.time.LocalDateTime;

public record PaymentResponse(
        String paymentId,
        String orderId,
        String paymentInfo,
        Long amount,
        LocalDateTime paymentDate
) {
    public static PaymentResponse from(PaymentDto dto) {
        return new PaymentResponse(
                dto.paymentId(),
                dto.orderId(),
                dto.paymentInfo(),
                dto.amount(),
                dto.createdAt()
        );
    }
}
