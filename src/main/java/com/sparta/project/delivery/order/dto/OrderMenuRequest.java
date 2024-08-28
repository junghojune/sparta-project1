package com.sparta.project.delivery.order.dto;

public record OrderMenuRequest(
        String menuId,
        int quantity
) {
    public OrderMenuDto toDto() {
        return OrderMenuDto.of(menuId, quantity);
    }
}
