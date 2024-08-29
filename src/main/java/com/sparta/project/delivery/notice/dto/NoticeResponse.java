package com.sparta.project.delivery.notice.dto;


import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NoticeResponse(
        String noticeId,
        String title,
        String content,
        Boolean isPublic,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {

    public static NoticeResponse from(NoticeDto dto) {
        return NoticeResponse.builder()
                .noticeId(dto.noticeId())
                .title(dto.title())
                .content(dto.content())
                .isPublic(dto.isPublic())
                .createdAt(dto.createdAt())
                .createdBy(dto.createdBy())
                .updatedAt(dto.updatedAt())
                .updatedBy(dto.updatedBy())
                .build();
    }
}
