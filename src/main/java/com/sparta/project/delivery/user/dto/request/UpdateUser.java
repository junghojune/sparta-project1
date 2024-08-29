package com.sparta.project.delivery.user.dto.request;

import com.sparta.project.delivery.user.dto.UserDto;
import lombok.Builder;

@Builder
public record UpdateUser (
        String email,
        String username,
        String password
){
    public UserDto toDto() {
        return UserDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .build();
    }
}
