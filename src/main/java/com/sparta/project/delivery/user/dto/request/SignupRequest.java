package com.sparta.project.delivery.user.dto.request;

import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.user.dto.UserDto;
import lombok.Builder;

// 회원가입 시 입력 해야 할 정보
@Builder
public record SignupRequest(
        String username,
        String email,
        String password,
        String role
){
    public UserDto toDto() {
        UserRoleEnum userRole;
        try {
            userRole = UserRoleEnum.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        return UserDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(userRole)
                .build();
    }
}
