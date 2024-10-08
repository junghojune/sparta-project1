package com.sparta.project.delivery.category.dto;

public record CategoryRequest(
        String name
) {
    public CategoryDto toDto(){
        return CategoryDto.of(name);
    }
}
