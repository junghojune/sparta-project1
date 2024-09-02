package com.sparta.project.delivery.payment.repository;

import com.sparta.project.delivery.payment.entity.Payment;
import com.sparta.project.delivery.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    Page<Payment> findAllByUser(User user, Pageable pageable);
    Optional<Payment> findByPaymentIdAndUser(String paymentId, User user);
}
