package com.sparta.project.delivery.notice.entity;

import com.sparta.project.delivery.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_notice")
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "notice_id", updatable = false, nullable = false)
    private String noticeId;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Lob
    @Column(nullable = false)
    private String content;

}