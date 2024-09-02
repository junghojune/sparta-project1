package com.sparta.project.delivery.address.dto.request;

import com.sparta.project.delivery.address.dto.AddressDto;

public record CreateAddress(
        String address

) {
    public AddressDto toDto() {
        return AddressDto.builder()
                .address(address)
                .build();
    }
}