package com.sparta.project.delivery.region.entity;

import com.sparta.project.delivery.common.BaseEntity;
import com.sparta.project.delivery.common.type.City;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_region")
public class Region extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "region_id")
    private String regionId;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City city;

    @Setter
    @Column(length = 10)
    private String siGun;

    @Setter
    @Column(length = 10)
    private String gu;

    @Setter
    @Column(length = 20)
    private String village;
}
