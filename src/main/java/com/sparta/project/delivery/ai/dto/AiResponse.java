package com.sparta.project.delivery.ai.dto;

public record AiResponse(
        String aiId,
        String question,
        String answer
) {
    public static AiResponse from(AiDto dto) {
        return new AiResponse(dto.aiId(), dto.question(), dto.answer());
    }
}
