package com.sparta.project.delivery.menu.dto.request;

import com.sparta.project.delivery.menu.dto.MenuDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateMenu(
        @NotBlank(message = "메뉴 이름은 필수입니다.")
        @Size(max = 100, message = "메뉴 이름은 100자를 넘을 수 없습니다.")
        String name,

        @NotNull(message = "가격은 필수입니다.")
        @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
        Long price,

        @Size(max = 500, message = "설명은 500자를 넘을 수 없습니다.")
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
