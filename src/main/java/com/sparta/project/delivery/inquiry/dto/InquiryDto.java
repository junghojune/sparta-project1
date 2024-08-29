package com.sparta.project.delivery.inquiry.dto;

import com.sparta.project.delivery.inquiry.constant.InquiryStatus;
import com.sparta.project.delivery.inquiry.entity.Inquiry;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record InquiryDto(
        String inquiryId,
        String userEmail,
        String title,
        String content,
        InquiryStatus status,
        String response,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {

    public static InquiryDto from(Inquiry entity) {
        return InquiryDto.builder()
                .inquiryId(entity.getInquiryId())
                .userEmail(entity.getUser().getEmail())
                .title(entity.getTitle())
                .content(entity.getContent())
                .status(entity.getStatus())
                .response(entity.getResponse())
                .isPublic(entity.getIsPublic())
                .isDeleted(entity.getIsDeleted())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .deletedAt(entity.getDeletedAt())
                .deletedBy(entity.getDeletedBy())
                .build();
    }
}
