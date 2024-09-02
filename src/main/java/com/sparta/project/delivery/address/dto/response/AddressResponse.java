package com.sparta.project.delivery.address.dto.response;

import com.sparta.project.delivery.address.dto.AddressDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AddressResponse(
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

    // DTO to Response
    public static AddressResponse from(AddressDto dto){
        return AddressResponse.builder()
                .address_id(dto.address_id())
                .user_id(dto.user_id())
                .address(dto.address())
                .isPublic(dto.isPublic())
                .isDeleted(dto.isDeleted())
                .createdAt(dto.createdAt())
                .createdBy(dto.createdBy())
                .updatedAt(dto.updatedAt())
                .updatedBy(dto.updatedBy())
                .deletedAt(dto.deletedAt())
                .deletedBy(dto.deletedBy())
                .build();
    }
}
