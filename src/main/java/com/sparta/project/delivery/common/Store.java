package com.sparta.project.delivery.common;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_store")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String storeId;

    // User 엔티티 작성 후 변경
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "owner_id", nullable = false)
    @Setter
    private String owner;

    // Region 엔티티 작성 후 변경
    // @OneToOne
    // @JoinColumn(name = "region_id", nullable = false)
    @Setter
    private String region;

    // Category 엔티티 작성 후 변경
    // @OneToOne
    // @JoinColumn(name = "category_id", nullable = false)
    @Setter
    private String category;

    @Setter
    @Column(length = 100, nullable = false)
    private String name;

    @Setter
    @Column(length = 100, nullable = false)
    private String address;


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
