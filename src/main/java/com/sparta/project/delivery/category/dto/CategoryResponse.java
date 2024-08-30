package com.sparta.project.delivery.category.dto;

public record CategoryResponse(
        String categoryId,
        String name
) {
    public static CategoryResponse of(String categoryId, String name) {
        return new CategoryResponse(categoryId, name);
    }

    public static CategoryResponse from(CategoryDto dto) {
        return new CategoryResponse(dto.categoryId(), dto.name());
    }
}
