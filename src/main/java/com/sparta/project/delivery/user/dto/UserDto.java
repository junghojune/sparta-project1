package com.sparta.project.delivery.user.dto;

import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
public record UserDto (
        Long userId,
        String email,

        String username,

        String password,
        UserRoleEnum role,
        boolean is_public,
        boolean is_deleted,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy

        ){


        public User toEntity() {
                return User.builder()
                        .userId(userId) // Optional: `userId` can be omitted if auto-generated
                        .email(email)
                        .username(username)
                        .password(password)
                        .role(role)
                        .build();
        }

        public static UserDto from(User entity) {
                return UserDto.builder()
                        .userId(entity.getUserId())
                        .email(entity.getEmail())
                        .username(entity.getUsername())
                        .password(entity.getPassword()) // Typically, password is not included in DTO for security reasons
                        .role(entity.getRole())
                        .is_public(entity.getIsPublic())
                        .is_deleted(entity.getIsDeleted())
                        .createdAt(entity.getCreatedAt())
                        .createdBy(entity.getCreatedBy())
                        .updatedAt(entity.getUpdatedAt())
                        .updatedBy(entity.getUpdatedBy())
                        .deletedAt(entity.getDeletedAt())
                        .deletedBy(entity.getDeletedBy())
                        .build();
        }
}

