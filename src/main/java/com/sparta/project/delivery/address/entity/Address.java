package com.sparta.project.delivery.address.entity;

import com.sparta.project.delivery.common.BaseEntity;
import com.sparta.project.delivery.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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

    public void deleteAddress(LocalDateTime time, String deletedBy){
        setIsPublic(false);
        setIsDeleted(true);
        setDeletedAt(time);
        setDeletedBy(deletedBy);
    }
}

