package com.sparta.project.delivery.user;

import com.sparta.project.delivery.common.UserRoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "p_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String user_id;

    // 사용자 이름
    @Column(length = 100, nullable = false)
    private String name;

    // 사용자 이메일 (로그인 시 사용)
    @Column(length = 255, nullable = false)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    // role
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    // 공개 여부
    @Column(length = 100, nullable = false)
    private boolean is_public;

    // 삭제 여부
    @Column(length = 100, nullable = false)
    private boolean is_deleted;

    // Audit 필드 (생성, 수정, 삭제 기록)
    private LocalDateTime createdAt;
    private String createBby;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime deletedAt;
    private String deletedBy;
}