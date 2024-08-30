package com.sparta.project.delivery.ai.Entity;

import com.sparta.project.delivery.common.BaseEntity;
import com.sparta.project.delivery.user.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_ai")
public class Ai extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ai_id")
    private String aiId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Column(length = 100, nullable = false)
    private String question;

    @Column(length = 100, nullable = false)
    private String answer;
}
