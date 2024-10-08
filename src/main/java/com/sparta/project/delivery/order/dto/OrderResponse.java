package com.sparta.project.delivery.order.dto;

import java.time.LocalDateTime;

public record OrderResponse(
        String orderId,
        String storeName,
        String orderType,
        Long price,
        String addressId,
        String address,
        LocalDateTime orderDate
) {

    public static OrderResponse from(OrderDto dto) {
        return new OrderResponse(dto.orderId(), dto.storeName(), dto.type().getName(), dto.price(), dto.addressId(), dto.DeliveryAddress(), dto.createdAt());
    }
}
