package com.sparta.project.delivery.inquiry.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record InquiryResponse(
        String inquiryId,
        String title,
        String userEmail,
        String content,
        Boolean isPublic,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {

    public static InquiryResponse from(InquiryDto dto) {
        return InquiryResponse.builder()
                .inquiryId(dto.inquiryId())
                .title(dto.title())
                .userEmail(dto.userEmail())
                .content(dto.content())
                .isPublic(dto.isPublic())
                .createdAt(dto.createdAt())
                .createdBy(dto.createdBy())
                .updatedAt(dto.updatedAt())
                .updatedBy(dto.updatedBy())
                .build();
    }
}
