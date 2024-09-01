package com.sparta.project.delivery.store.dto.request;

import com.sparta.project.delivery.store.dto.StoreDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;


@Builder
public record CreateStore(
        @NotBlank(message = "지역 ID는 필수입니다.")
        String regionId,

        @NotBlank(message = "카테고리 ID는 필수입니다.")
        String categoryId,

        @NotBlank(message = "가게 이름은 필수입니다.")
        @Size(max = 100, message = "가게 이름은 최대 100자까지 허용됩니다.")
        String name,

        @Size(max = 3, message = "가게 설명은 3글자 이상이어야 합니다.")
        String description,

        //주소는 후에 저장할 수 있도록 허용
        @Size(max = 200, message = "주소는 최대 200자까지 허용됩니다.")
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
