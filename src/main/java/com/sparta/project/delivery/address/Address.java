package com.sparta.project.delivery.address;

import com.sparta.project.delivery.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "p_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String address_id;

    // user과 연관 관계 생성
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 주소
    @Column(length = 100, nullable = false)
    private String address;


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