package com.sparta.project.delivery.store.dto.request;

import com.sparta.project.delivery.store.dto.StoreDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateStore(
        @NotBlank(message = "가게 이름은 필수 입력 값입니다.")
        @Size(max = 100, message = "가게 이름은 100자 이하여야 합니다.")
        String name,

        @NotBlank(message = "주소는 필수 입력 값입니다.")
        @Size(max = 100, message = "주소는 100자 이하여야 합니다.")
        String address,

        @Size(max = 3, message = "가게 설명은 3글자 이상이어야 합니다.")
        String description,

        @NotNull(message = "공개 상태는 필수 입력 값입니다.")
        Boolean isPublic
) {

    public StoreDto toDto() {
        return StoreDto.builder()
                .name(name)
                .address(address)
                .description(description)
                .build();
    }
}
