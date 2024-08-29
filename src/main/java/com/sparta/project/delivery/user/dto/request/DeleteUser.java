package com.sparta.project.delivery.user.dto.request;

import com.sparta.project.delivery.user.dto.UserDto;
import lombok.Builder;

@Builder
public record DeleteUser(
        String password
) {
    public UserDto toDto() {
        return UserDto.builder()
                .password(password)
                .build();
    }
}
