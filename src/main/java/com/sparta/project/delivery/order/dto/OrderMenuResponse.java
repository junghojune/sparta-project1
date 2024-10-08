package com.sparta.project.delivery.order.dto;

public record OrderMenuResponse(
        String orderMenuId,
        String menuId,
        String menuName,
        int quantity
) {
    public static OrderMenuResponse from(OrderMenuDto dto) {
        return new OrderMenuResponse(
                dto.orderMenuId(),
                dto.menuId(),
                dto.menuName(),
                dto.quantity()
        );
    }
}
