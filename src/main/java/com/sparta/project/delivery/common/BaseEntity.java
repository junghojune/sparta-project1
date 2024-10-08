package com.sparta.project.delivery.common;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Setter
    @ColumnDefault(value = "true")
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    @Setter
    @ColumnDefault(value = "false")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", length = 100, nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Setter
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Setter
    @Column(name = "deleted_by", length = 100)
    private String deletedBy;


    // 기본 생성자에서 기본값 설정 안하면 default 값이 안넣어짐
    public BaseEntity() {
        this.isPublic = true;
        this.isDeleted = false;
    }
}