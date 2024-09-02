package com.sparta.project.delivery.region.dto;

import com.sparta.project.delivery.common.type.City;

public record RegionResponse(
        String regionId,
        String city,
        String siGun,
        String gu,
        String village
) {
    public static RegionResponse of(String regionId,
                                    City city,
                                    String siGun,
                                    String gu,
                                    String village) {
        return new RegionResponse(regionId, city.getName(), siGun, gu, village);
    }

    public static RegionResponse from(RegionDto dto) {
        return RegionResponse.of(dto.regionId(), dto.city(), dto.siGun(), dto.gu(), dto.village());
    }
}
