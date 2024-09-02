package com.sparta.project.delivery.payment.service;

import com.sparta.project.delivery.common.exception.CustomException;
import com.sparta.project.delivery.order.entity.Order;
import com.sparta.project.delivery.order.repository.OrderRepository;
import com.sparta.project.delivery.payment.dto.PaymentDto;
import com.sparta.project.delivery.payment.repository.PaymentRepository;
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.dto.UserDto;
import com.sparta.project.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.project.delivery.common.exception.DeliveryError.*;
import static com.sparta.project.delivery.common.type.UserRoleEnum.CUSTOMER;
import static com.sparta.project.delivery.common.type.UserRoleEnum.MASTER;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Page<PaymentDto> getPaymentsByUser(UserDto userDto, Pageable pageable) {
        User user = userRepository.findById(userDto.userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        checkUserRole(user);

        return paymentRepository.findAllByUser(user, pageable).map(PaymentDto::from);
    }

    @Transactional(readOnly = true)
    public PaymentDto getPaymentByPaymentId(UserDto userDto, String paymentId) {

        User user = userRepository.findById(userDto.userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        checkUserRole(user);

        return PaymentDto.from(
                paymentRepository.findByPaymentIdAndUser(
                                paymentId, user
                        )
                        .orElseThrow(() -> new CustomException(PAYMENT_NOT_FOUND))
        );
    }

    @Transactional
    public void createPayment(PaymentDto dto) {
        User user = userRepository.findById(dto.userDto().userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        checkUserRole(user);

        Order order = orderRepository.findByOrderIdAndUser(dto.orderId(), user)
                .orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));

        // 외부 API 연동

        paymentRepository.save(dto.toEntity(user, order));
    }

    private void checkUserRole(User user) {
        if (!user.getRole().equals(CUSTOMER) && !user.getRole().equals(MASTER)) {
            throw new CustomException(AUTH_INVALID_CREDENTIALS);
        }
    }
}
