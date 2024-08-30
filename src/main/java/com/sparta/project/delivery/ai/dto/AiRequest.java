package com.sparta.project.delivery.ai.dto;

import com.sparta.project.delivery.user.dto.UserDto;

public record AiRequest(
        String question
) {
    public AiDto toDto(UserDto dto) {
        return AiDto.of(dto, question);
    }
}
