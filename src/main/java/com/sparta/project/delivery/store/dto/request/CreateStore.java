package com.sparta.project.delivery.store.dto.request;

import com.sparta.project.delivery.store.dto.StoreDto;
import lombok.Builder;


@Builder
public record CreateStore(
        String regionId,
        String categoryId,
        String name,
        String address
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
