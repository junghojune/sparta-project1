package com.sparta.project.delivery.store.dto.request;

import com.sparta.project.delivery.store.dto.StoreDto;

public record UpdateStore(
        String regionId,
        String categoryId,
        String name,
        String address,
        Boolean isPublic
) {

    public StoreDto toDto() {
        return StoreDto.builder()
                .regionId(regionId)
                .categoryId(categoryId)
                .name(name)
                .address(address)
                .build();
    }
}
