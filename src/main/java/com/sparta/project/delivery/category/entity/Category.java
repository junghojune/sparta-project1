package com.sparta.project.delivery.category.entity;

import com.sparta.project.delivery.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_category")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private String categoryId;

    @Setter
    @Column(length = 36, nullable = false)
    private String name;

    private Category (String name){
        this.name = name;
    }

    public static Category of(String name){
        return new Category(name);
    }
}
