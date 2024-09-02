package com.sparta.project.delivery.address.dto;

import com.sparta.project.delivery.address.entity.Address;
import com.sparta.project.delivery.user.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AddressDto(
        String address_id,
        Long user_id,
        String address,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {

    // DTO to Entity
    public Address toEntity(User user){
        return Address.builder()
                .addressId(address_id)
                .user(user)
                .address(address)
                .build();
    }

    // Entity to DTO
    public static AddressDto from(Address entity) {
        return AddressDto.builder()
                .address_id(entity.getAddressId())
                .user_id(entity.getUser().getUserId())
                .address(entity.getAddress())
                .isPublic(entity.getIsPublic())
                .isDeleted(entity.getIsDeleted())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .deletedAt(entity.getDeletedAt())
                .deletedBy(entity.getDeletedBy())
                .build();
    }
}