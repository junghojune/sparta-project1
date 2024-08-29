package com.sparta.project.delivery.order.service;

import com.sparta.project.delivery.address.Address;
import com.sparta.project.delivery.address.AddressRepository;
import com.sparta.project.delivery.common.type.OrderStatus;
import com.sparta.project.delivery.menu.entity.Menu;
import com.sparta.project.delivery.menu.repository.MenuRepository;
import com.sparta.project.delivery.order.dto.OrderDto;
import com.sparta.project.delivery.order.dto.OrderMenuDto;
import com.sparta.project.delivery.order.dto.OrderWithMenuDto;
import com.sparta.project.delivery.order.entity.Order;
import com.sparta.project.delivery.order.entity.OrderMenu;
import com.sparta.project.delivery.order.repository.OrderMenuRepository;
import com.sparta.project.delivery.order.repository.OrderRepository;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.store.repository.StoreRepository;
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public void createOrder(OrderDto orderDto, List<OrderMenuDto> orderMenuDtoList) {
        // userDto -> user 변환
        User user = orderDto.user();

        // store
        Store store = storeRepository.findById(orderDto.storeId()).orElseThrow();

        // address
        Address address = addressRepository.findByAddressIdAndUser(orderDto.addressId(), user).orElseThrow();

        // order
        Order order = orderDto.toEntity(user, store, address);
        orderRepository.save(order);


        //orderMenu
        Long totalPrice = 0L;
        List<OrderMenu> orderMenuList = new ArrayList<>();
        for (OrderMenuDto orderMenuDto : orderMenuDtoList) {
            Menu menu = menuRepository.findById(orderMenuDto.menuId()).orElseThrow();
            totalPrice += menu.getPrice();
            orderMenuList.add(orderMenuDto.of(order, menu));
        }

        if (totalPrice != order.getPrice()) {
            throw new IllegalArgumentException("가격이 정확하지 않습니다.");
        }

        orderMenuRepository.saveAll(orderMenuList);
    }

    public Page<OrderDto> getOrdersByUser(User user, Pageable pageable) {
        return orderRepository.findAllByUser(user, pageable).map(OrderDto::from);


    }

    public Page<OrderDto> getOrdersByStore(String storeId, Pageable pageable) {
        Store store = storeRepository.findById(storeId).orElseThrow();
        return orderRepository.findAllByStore(store, pageable).map(OrderDto::from);
    }

    public OrderWithMenuDto getOrderByOrderId(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        return OrderWithMenuDto.from(order);
    }

    @Transactional
    public void approveOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        order.setStatus(OrderStatus.ORDER_COMPLETED);
    }

    @Transactional
    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        LocalDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        LocalDateTime orderTime = order.getCreatedAt();

        if (orderTime.plusMinutes(5).isBefore(now)) {
            throw new IllegalArgumentException("주문 취소 시간이 지났습니다.");
        }

        order.setStatus(OrderStatus.ORDER_CANCELLED);
    }
}
