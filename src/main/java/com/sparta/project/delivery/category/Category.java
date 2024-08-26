package com.sparta.project.delivery.category;import jakarta.persistence.Entity;import jakarta.persistence.Id;import jakarta.persistence.Table;import lombok.AllArgsConstructor;import lombok.Getter;import lombok.RequiredArgsConstructor;import java.time.LocalDateTime;@RequiredArgsConstructor@AllArgsConstructor@Getter@Entity@Table(name = "p_category")public class Category {    @Id    private String categoryId;    private String name;    private Boolean isPublic;    private Boolean isDeleted;    private LocalDateTime createdAt;    private String createBby;    private LocalDateTime updatedAt;    private String updatedBy;    private LocalDateTime deletedAt;    private String deletedBy;}