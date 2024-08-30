package com.sparta.project.delivery.menu.dto.request;

import com.sparta.project.delivery.menu.dto.MenuDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UpdateMenu(
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        @Size(max = 100, message = "이름은 100자를 초과할 수 없습니다.")
        String name,

        @NotNull(message = "가격은 필수 입력 값입니다.")
        @Positive(message = "가격은 양수여야 합니다.")
        Long price,

        @Size(max = 500, message = "설명은 500자를 초과할 수 없습니다.")
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
