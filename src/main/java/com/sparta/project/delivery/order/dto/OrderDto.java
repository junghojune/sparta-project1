package com.sparta.project.delivery.order.dto;

import com.sparta.project.delivery.address.entity.Address;
import com.sparta.project.delivery.common.type.DeliveryType;
import com.sparta.project.delivery.common.type.OrderStatus;
import com.sparta.project.delivery.order.entity.Order;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.dto.UserDto;

import java.time.LocalDateTime;

import static com.sparta.project.delivery.common.type.OrderStatus.ORDER_IN_PROGRESS;

public record OrderDto(
        String orderId,
        String request,
        OrderStatus status,
        DeliveryType type,
        Long price,
        UserDto userDto,
        String storeId,
        String storeName,
        String addressId,
        String DeliveryAddress,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {
    public static OrderDto of(UserDto userDto, String request, DeliveryType type, Long price, String storeId, String addressId) {
        return new OrderDto(null, request, ORDER_IN_PROGRESS, type, price, userDto, storeId, null, addressId, null,
                null, null, null, null, null, null, null, null);
    }

    public static OrderDto from(Order entity) {
        return new OrderDto(
                entity.getOrderId(),
                entity.getRequest(),
                entity.getStatus(),
                entity.getType(),
                entity.getPrice(),
                UserDto.from(entity.getUser()),
                entity.getStore().getStoreId(),
                entity.getStore().getName(),
                entity.getAddress().getAddressId(),
                entity.getAddress().getAddress(),
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

    public Order toEntity(User user, Store store, Address address) {
        return Order.of(
                user, store, address, request, status, type, price
        );
    }

}
