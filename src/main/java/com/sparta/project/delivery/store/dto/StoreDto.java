package com.sparta.project.delivery.store.dto;

import com.sparta.project.delivery.category.entity.Category;
import com.sparta.project.delivery.common.type.City;
import com.sparta.project.delivery.menu.dto.MenuDto;
import com.sparta.project.delivery.region.entity.Region;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.user.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record StoreDto(
        String storeId,
        String userEmail,
        String userName,
        String regionId,
        City city,
        String siGun,
        String gu,
        String village,
        String categoryId,
        String categoryName,
        String name,
        String address,
        String description,
        Float averageRating,
        Integer reviewCount,
        Set<MenuDto> menus,
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
                .description(description)
                .build();
    }

    public static StoreDto from(Store entity){
        return StoreDto.builder()
                .storeId(entity.getStoreId())
                .userEmail(entity.getUser().getEmail())
                .userName(entity.getUser().getUsername())
                .regionId(entity.getRegion().getRegionId())
                .city(entity.getRegion().getCity())
                .siGun(entity.getRegion().getSiGun())
                .gu(entity.getRegion().getGu())
                .village(entity.getRegion().getVillage())
                .categoryId(entity.getCategory().getCategoryId())
                .categoryName(entity.getCategory().getName())
                .name(entity.getName())
                .address(entity.getAddress())
                .description(entity.getDescription())
                .averageRating(entity.getAverageRating())
                .reviewCount(entity.getReviewCount())
                .menus(entity.getMenus().stream()
                        .map(MenuDto::from)
                        .collect(Collectors.toUnmodifiableSet())
                )
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