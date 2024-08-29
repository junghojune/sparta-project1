package com.sparta.project.delivery.user.dto.request;

import com.sparta.project.delivery.user.dto.UserDto;
import lombok.Builder;

// 로그인 시 입력해야 할 정보
@Builder
public record LoginRequest(
        String email,
        String password
) {
    public UserDto toDto() {
        return UserDto.builder()
                .email(email)
                .password(password)
                .build();
    }
}
