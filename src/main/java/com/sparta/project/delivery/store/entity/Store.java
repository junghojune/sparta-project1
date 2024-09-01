
package com.sparta.project.delivery.store.entity;


import com.sparta.project.delivery.category.entity.Category;
import com.sparta.project.delivery.common.BaseEntity;
import com.sparta.project.delivery.menu.entity.Menu;
import com.sparta.project.delivery.region.entity.Region;
import com.sparta.project.delivery.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_store")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_id")
    private String storeId;

    @Setter
    @Column(length = 100, nullable = false)
    private String name;

    @Setter
    @Column(length = 200)
    private String address;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @Lob
    @Column
    private String description;

    @Setter
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @Setter
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Setter
    @Column(name = "average_rating", nullable = false)
    @Builder.Default
    private float averageRating = 0;

    @Setter
    @Column(name = "review_count", nullable = false)
    @Builder.Default
    private int reviewCount = 0;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Menu> menus = new LinkedHashSet<>();

    public void deleteStore(LocalDateTime time, String userEmail){
        setIsPublic(false);
        setIsDeleted(true);
        setDeletedAt(time);
        setDeletedBy(userEmail);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(storeId, store.storeId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(storeId);
    }
}
