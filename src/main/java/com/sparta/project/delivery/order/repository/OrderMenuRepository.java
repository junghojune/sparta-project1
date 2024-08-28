package com.sparta.project.delivery.order.repository;

import com.sparta.project.delivery.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, String> {
}
