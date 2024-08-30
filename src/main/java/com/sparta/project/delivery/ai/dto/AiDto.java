package com.sparta.project.delivery.ai.dto;

import com.sparta.project.delivery.ai.Entity.Ai;
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.dto.UserDto;

import java.time.LocalDateTime;

public record AiDto(
        String aiId,
        UserDto userDto,
        String question,
        String answer,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {
    public static AiDto of(UserDto userDto, String question) {
        return new AiDto(null, userDto, question, null, null, null, null, null, null, null, null, null);
    }

    public static AiDto from(Ai ai) {
        return new AiDto(
                ai.getAiId(),
                UserDto.from(ai.getUser()),
                ai.getQuestion(),
                ai.getAnswer(),
                ai.getIsPublic(),
                ai.getIsDeleted(),
                ai.getCreatedAt(),
                ai.getCreatedBy(),
                ai.getUpdatedAt(),
                ai.getUpdatedBy(),
                ai.getDeletedAt(),
                ai.getDeletedBy()
        );
    }

    public Ai toEntity(User user, String answer) {
        return Ai.builder()
                .user(user)
                .question(question)
                .answer(answer)
                .build();
    }
}
