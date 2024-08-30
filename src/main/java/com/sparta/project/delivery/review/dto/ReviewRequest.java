package com.sparta.project.delivery.review.dto;

import com.sparta.project.delivery.user.dto.UserDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReviewRequest(
        String orderId,

        @Min(value = 1, message = "점수는 최소 1점이어야 합니다.")
        @Max(value = 5, message = "점수는 최대 5점이어야 합니다.")
        int rating,

        @NotBlank(message = "점수의 이유를 작성하셔야 합니다.")
        @Size(max = 500, message = "리뷰는 최대 500자까지 허용됩니다.")
        String comment
) {
    public ReviewDto toDto(UserDto userDto) {
        return ReviewDto.of(
                userDto, orderId, rating, comment
        );
    }
}
