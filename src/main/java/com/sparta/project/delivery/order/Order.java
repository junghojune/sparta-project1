package com.sparta.project.delivery.order;

import com.sparta.project.delivery.address.Address;
import com.sparta.project.delivery.common.BaseEntity;
import com.sparta.project.delivery.common.type.DeliveryType;
import com.sparta.project.delivery.common.type.OrderStatus;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_order")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private String orderId;

    @Column(length = 50)
    private String request;

    // 주문 상태(주문 진행, 주문 완료, 주문 취소, 음식 준비 완료, 배달중, 배달 완료, 픽업 완료)
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    // 음식 받는 방식(배달, 픽업)
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "order")
    @Builder.Default
    private List<OrderMenu> orderMenuList = new ArrayList<>();
}
