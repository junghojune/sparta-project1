package com.sparta.project.delivery.menu.entity;


import com.sparta.project.delivery.common.BaseEntity;
import com.sparta.project.delivery.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_menu")
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "menu_id", nullable = false)
    private String menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Setter
    @Column(length = 100, nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private Long price;

    @Setter
    @Column(length = 500)
    private String description;


    public void deleteMenu(LocalDateTime time, String deletedBy){
        setIsPublic(false);
        setIsDeleted(true);
        setDeletedAt(time);
        setDeletedBy(deletedBy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(menuId, menu.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(menuId);
    }
}
