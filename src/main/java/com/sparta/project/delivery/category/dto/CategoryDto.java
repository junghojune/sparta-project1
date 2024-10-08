package com.sparta.project.delivery.category.dto;

import com.sparta.project.delivery.category.entity.Category;

import java.time.LocalDateTime;

public record CategoryDto(
        String categoryId,
        String name,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {

    public static CategoryDto of(String name) {
        return new CategoryDto(null, name, null, null, null, null, null, null, null, null);
    }

    public static CategoryDto of(String categoryId, String name, Boolean isPublic, Boolean isDeleted) {

        return new CategoryDto(categoryId, name, isPublic, isDeleted,
                null, null, null, null, null, null);
    }

    public static CategoryDto from(Category category) {
        return new CategoryDto(
                category.getCategoryId(),
                category.getName(),
                category.getIsPublic(),
                category.getIsDeleted(),
                category.getCreatedAt(),
                category.getCreatedBy(),
                category.getUpdatedAt(),
                category.getUpdatedBy(),
                category.getDeletedAt(),
                category.getDeletedBy()
        );
    }

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .build();
    }
}
