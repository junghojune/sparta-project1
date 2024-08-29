package com.sparta.project.delivery.notice.dto;

import com.sparta.project.delivery.notice.entity.Notice;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NoticeDto(
        String noticeId,
        String title,
        String content,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {

    public static NoticeDto from(Notice entity) {
        return NoticeDto.builder()
                .noticeId(entity.getNoticeId())
                .title(entity.getTitle())
                .content(entity.getContent())
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

    public Notice toEntity(){
        return Notice.builder()
                .title(title)
                .content(content)
                .build();
    }
}