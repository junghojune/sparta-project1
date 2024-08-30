package com.sparta.project.delivery.region.dto;

import com.sparta.project.delivery.common.type.City;

public record RegionRequest(
        City city,
        String siGun,
        String gu,
        String village
) {
    public RegionDto toDto() {
        return RegionDto.of(city, siGun, gu, village);
    }
}
