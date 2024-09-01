package com.sparta.project.delivery.store.dto.response;

import com.sparta.project.delivery.region.dto.RegionDto;
import com.sparta.project.delivery.region.dto.RegionResponse;
import com.sparta.project.delivery.store.dto.StoreDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record StoreResponse(
        String storeId,
        String userEmail,
        RegionResponse region,
        String category,
        String name,
        String address,
        String description,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {


    public static StoreResponse from(StoreDto dto) {
        return StoreResponse.builder()
                .storeId(dto.storeId())
                .userEmail(dto.userEmail())
                .region(RegionResponse.from(
                        RegionDto.of(dto.city(),dto.siGun(),dto.gu(),dto.village()))
                )
                .category(dto.categoryName())
                .name(dto.name())
                .address(dto.address())
                .description(dto.description())
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
