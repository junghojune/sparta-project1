package com.sparta.project.delivery.payment.dto;

import com.sparta.project.delivery.user.dto.UserDto;

public record PaymentRequest(
        String orderId,
        String paymentInfo,
        Long amount
) {
    public PaymentDto toDto(UserDto userDto){
        return PaymentDto.of(userDto, orderId, paymentInfo, amount);
    }
}
