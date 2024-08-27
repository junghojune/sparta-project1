package com.sparta.project.delivery.store.dto.response;

import com.sparta.project.delivery.category.dto.CategoryResponse;
import com.sparta.project.delivery.region.entity.Region;
import com.sparta.project.delivery.user.User;
import lombok.Builder;

import java.time.LocalDateTime;

//TODO : 다른 엔티티 정리되면 적용
@Builder
public record StoreResponse(
        String storeId,
        User user, // TODO : UserDto 개발 후 변경
        Region region, // TODO : RegionDto 개발 후 변경
        CategoryResponse category,
        String name,
        String address,
        Boolean isPublic,
        Boolean isDeleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {

}
