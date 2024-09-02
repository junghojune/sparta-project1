package com.sparta.project.delivery.payment.entity;

import com.sparta.project.delivery.common.BaseEntity;
import com.sparta.project.delivery.order.entity.Order;
import com.sparta.project.delivery.user.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_payment")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id")
    private String paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "payment_info", columnDefinition = "json")
    private String paymentInfo;

    @Column(name = "amount", nullable = false)
    private Long amount;
}
