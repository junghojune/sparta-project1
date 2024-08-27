package com.sparta.project.delivery.menu.dto.response;

import com.sparta.project.delivery.menu.dto.MenuDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MenuResponse(
        String menuId,
        String storeId,
        String name,
        Long price,
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

    public static MenuResponse from(MenuDto dto){
        return MenuResponse.builder()
                .menuId(dto.menuId())
                .storeId(dto.storeId())
                .name(dto.name())
                .price(dto.price())
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
