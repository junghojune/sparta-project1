package com.sparta.project.delivery.inquiry.entity;

import com.sparta.project.delivery.common.BaseEntity;
import com.sparta.project.delivery.user.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_inquiry")
public class Inquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "inquiry_id", updatable = false, nullable = false)
    private String inquiryId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Lob
    @Column(nullable = false)
    private String content;

    @Setter
    @Column(length = 50, nullable = false)
    private String status;

    @Setter
    @Lob
    @Column
    private String response;



}
