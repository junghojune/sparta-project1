package com.sparta.project.delivery.order.dto;

import com.sparta.project.delivery.common.type.DeliveryType;
import com.sparta.project.delivery.user.User;

import java.util.List;

public record OrderRequest(
        String storeId,
        String addressId,
        String request,
        DeliveryType type,
        Long price,
        List<OrderMenuRequest> menus
) {
    public OrderDto toDto(User user) {
        return OrderDto.of(user, request, type, price, storeId, addressId);
    }
}
