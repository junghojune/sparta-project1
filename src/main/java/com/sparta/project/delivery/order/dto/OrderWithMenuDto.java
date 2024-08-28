package com.sparta.project.delivery.order.dto;

import com.sparta.project.delivery.common.type.DeliveryType;
import com.sparta.project.delivery.common.type.OrderStatus;
import com.sparta.project.delivery.order.entity.Order;
import com.sparta.project.delivery.user.User;

import java.time.LocalDateTime;
import java.util.List;

public record OrderWithMenuDto(
        String orderId,
        String request,
        OrderStatus status,
        DeliveryType type,
        Long price,
        User user, // TODO : USERDTO로 변경
        String storeId,
        String storeName,
        String addressId,
        String DeliveryAddress,
        List<OrderMenuDto> orderMenuDtos,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {
    public static OrderWithMenuDto from(Order entity) {
        return new OrderWithMenuDto(
                entity.getOrderId(),
                entity.getRequest(),
                entity.getStatus(),
                entity.getType(),
                entity.getPrice(),
                entity.getUser(),
                entity.getStore().getStoreId(),
                entity.getStore().getName(),
                entity.getAddress().getAddressId(),
                entity.getAddress().getAddress(),

                entity.getOrderMenuList().stream()
                        .map(OrderMenuDto::from)
                        .toList()
                ,
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
}
