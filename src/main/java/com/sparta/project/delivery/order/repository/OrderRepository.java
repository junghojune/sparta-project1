package com.sparta.project.delivery.order.repository;

import com.sparta.project.delivery.order.entity.Order;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findAllByUser(User user, Pageable pageable);

    Page<Order> findAllByStore(Store store, Pageable pageable);
}