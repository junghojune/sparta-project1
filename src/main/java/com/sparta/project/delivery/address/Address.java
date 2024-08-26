package com.sparta.project.delivery.address;

import com.sparta.project.delivery.common.BaseEntity;
import com.sparta.project.delivery.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "p_address")
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id")
    private String addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @Column(length = 100, nullable = false)
    private String address;
}