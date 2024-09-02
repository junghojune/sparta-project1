package com.sparta.project.delivery.address.dto.request;

import com.sparta.project.delivery.address.dto.AddressDto;
import lombok.Builder;

@Builder
public record UpdateAddress(
        String address
) {
    public AddressDto toDto() {
        return AddressDto.builder()
                .address(address)
                .build();
    }
}
