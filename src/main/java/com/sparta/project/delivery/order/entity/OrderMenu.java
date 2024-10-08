package com.sparta.project.delivery.order.entity;

import com.sparta.project.delivery.common.BaseEntity;
import com.sparta.project.delivery.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "p_order_menu")
public class OrderMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_menu_id")
    private String orderMenuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    private int quantity;

    public static OrderMenu of(Order order, Menu menu, int quantity) {
        return OrderMenu.builder()
                .order(order)
                .menu(menu)
                .quantity(quantity)
                .build();
    }
}
