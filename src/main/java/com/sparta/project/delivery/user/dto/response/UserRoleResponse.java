package com.sparta.project.delivery.user.dto.response;

import com.sparta.project.delivery.common.type.UserRoleEnum;
import lombok.Builder;

@Builder
public record UserRoleResponse(
        Long userId,
        UserRoleEnum role
) {
}
