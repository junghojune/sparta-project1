package com.sparta.project.delivery.region.dto;

import com.sparta.project.delivery.common.type.City;
import com.sparta.project.delivery.region.entity.Region;

import java.time.LocalDateTime;

public record RegionDto(
        String regionId,
        City city,
        String siGun,
        String gu,
        String village,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {
    public static RegionDto of(City city, String siGun, String gu, String village) {
        return new RegionDto(null, city, siGun, gu, village, null, null, null, null, null, null, null, null);
    }

    public static RegionDto from(Region region) {
        return new RegionDto(
                region.getRegionId(),
                region.getCity(),
                region.getSiGun(),
                region.getGu(),
                region.getVillage(),
                region.getIsPublic(),
                region.getIsDeleted(),
                region.getCreatedAt(),
                region.getCreatedBy(),
                region.getUpdatedAt(),
                region.getUpdatedBy(),
                region.getDeletedAt(),
                region.getDeletedBy()
        );
    }

    public Region toEntity() {
        return Region.builder()
                .city(city)
                .siGun(siGun == null ? null : siGun)
                .gu(gu == null ? null : gu)
                .village(village == null ? null : village)
                .build();
    }
}
