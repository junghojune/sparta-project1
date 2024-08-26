package com.sparta.project.delivery.user;

import com.sparta.project.delivery.common.BaseEntity;
import com.sparta.project.delivery.common.type.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String userId;

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
}