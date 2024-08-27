package com.sparta.project.delivery.menu.dto;

import com.sparta.project.delivery.menu.entity.Menu;
import com.sparta.project.delivery.store.entity.Store;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MenuDto(
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

    public Menu toEntity(Store store) {
        return Menu.builder()
                .name(name)
                .store(store)
                .price(price)
                .description(description)
                .build();
    }


    public static MenuDto from(Menu entity) {
        return MenuDto.builder()
                .menuId(entity.getMenuId())
                .storeId(entity.getStore().getStoreId())
                .name(entity.getName())
                .price(entity.getPrice())
                .description(entity.getDescription())
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
