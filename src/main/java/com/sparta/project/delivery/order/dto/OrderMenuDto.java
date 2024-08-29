package com.sparta.project.delivery.order.dto;

import com.sparta.project.delivery.menu.entity.Menu;
import com.sparta.project.delivery.order.entity.Order;
import com.sparta.project.delivery.order.entity.OrderMenu;

import java.time.LocalDateTime;

public record OrderMenuDto(
        String orderMenuId,
        OrderDto orderDto,
        String menuId,
        String menuName,
        int quantity,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {
    public static OrderMenuDto of(String menuId, int quantity) {
        return new OrderMenuDto(null, null, menuId, null, quantity,
                null, null, null, null, null, null, null, null);
    }

    public static OrderMenuDto from(OrderMenu entity) {
        return new OrderMenuDto(
                entity.getOrderMenuId(),
                OrderDto.from(entity.getOrder()),
                entity.getMenu().getMenuId(),
                entity.getMenu().getName(),
                entity.getQuantity(),
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

    public OrderMenu of(Order order, Menu menu) {
        return OrderMenu.of(
                order, menu, quantity
        );
    }
}
