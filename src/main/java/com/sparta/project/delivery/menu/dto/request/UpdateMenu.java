package com.sparta.project.delivery.menu.dto.request;

import com.sparta.project.delivery.menu.dto.MenuDto;
import lombok.Builder;

@Builder
public record UpdateMenu(
        String name,
        Long price,
        String description
) {

    public MenuDto toDto() {
        return MenuDto.builder()
                .name(name)
                .price(price)
                .description(description)
                .build();
    }
}
