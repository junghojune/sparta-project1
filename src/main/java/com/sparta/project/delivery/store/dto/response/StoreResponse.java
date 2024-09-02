package com.sparta.project.delivery.store.dto.response;

import com.sparta.project.delivery.menu.dto.response.MenuResponse;
import com.sparta.project.delivery.region.dto.RegionDto;
import com.sparta.project.delivery.region.dto.RegionResponse;
import com.sparta.project.delivery.store.dto.StoreDto;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record StoreResponse(
        String storeId,
        String userEmail,
        RegionResponse region,
        String category,
        String name,
        String address,
        String description,
        Set<MenuResponse> menus,
        Float averageRating,
        Integer reviewCount,
        Boolean isPublic,
        Boolean isDeleted
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
                .menus(dto.menus().stream()
                        .map(MenuResponse::from)
                        .collect(Collectors.toUnmodifiableSet())
                )
                .averageRating(roundToOneDecimalPlace(dto.averageRating()))
                .reviewCount(dto.reviewCount())
                .isPublic(dto.isPublic())
                .isDeleted(dto.isDeleted())
                .build();
    }

    private static Float roundToOneDecimalPlace(Float value) {
        if (value == 0) {
            return 0f;
        }
        return Math.round(value * 10) / 10.0f;  // 소수점 첫째 자리까지 반올림
    }
}
