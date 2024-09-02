package com.sparta.project.delivery.order.dto;

import com.sparta.project.delivery.common.type.DeliveryType;
import com.sparta.project.delivery.user.dto.UserDto;

import java.util.List;

public record OrderRequest(
        String storeId,
        String addressId,
        String request,
        DeliveryType type,
        Long price,
        List<OrderMenuRequest> menus
) {
    public OrderDto toDto(UserDto userDto) {
        return OrderDto.of(userDto, request, type, price, storeId, addressId);
    }
}
