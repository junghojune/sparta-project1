package com.sparta.project.delivery.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderWithMenuResponse(
        String orderId,
        String storeId,
        String storeName,
        String deliveryType,
        String orderStatus,
        Long price,
        String addressId,
        String DeliveryAddress,
        List<OrderMenuResponse> menus,
        LocalDateTime orderDate,
        LocalDateTime orderCompleteDate
) {
    public static OrderWithMenuResponse from(OrderWithMenuDto dto) {
        return new OrderWithMenuResponse(
                dto.orderId(),
                dto.storeId(),
                dto.storeName(),
                dto.type().getName(),
                dto.status().getName(),
                dto.price(),
                dto.addressId(),
                dto.DeliveryAddress(),
                dto.orderMenuDtos().stream().map(OrderMenuResponse::from).toList(),
                dto.createdAt(),
                dto.updatedAt()
        );
    }
}
