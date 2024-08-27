package com.sparta.project.delivery.store.dto;

import com.sparta.project.delivery.category.entity.Category;
import com.sparta.project.delivery.common.type.City;
import com.sparta.project.delivery.region.entity.Region;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.user.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record StoreDto(
        String storeId,
        User user, // TODO : UserDto 개발 후 변경
        String regionId, //TODO : RegionDto 개발 후 변경
        City city,
        String siGun,
        String gu,
        String village,
        String categoryId,
        String categoryName,
        String name,
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

    public Store toEntity(User user, Region region, Category category){
        return Store.builder()
                .user(user)
                .region(region)
                .category(category)
                .name(name)
                .address(address)
                .build();
    }

    public static StoreDto from(Store entity){
        return StoreDto.builder()
                .storeId(entity.getStoreId())
                .user(entity.getUser())
                .regionId(entity.getRegion().getRegionId())
                .city(entity.getRegion().getCity())
                .siGun(entity.getRegion().getSiGun())
                .gu(entity.getRegion().getGu())
                .village(entity.getRegion().getVillage())
                .categoryId(entity.getCategory().getCategoryId())
                .categoryName(entity.getCategory().getName())
                .name(entity.getName())
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