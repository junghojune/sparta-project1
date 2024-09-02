package com.sparta.project.delivery.order.service;

import com.sparta.project.delivery.address.entity.Address;
import com.sparta.project.delivery.address.repository.AddressRepository;
import com.sparta.project.delivery.common.exception.CustomException;
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
import com.sparta.project.delivery.user.dto.UserDto;
import com.sparta.project.delivery.user.repository.UserRepository;
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

import static com.sparta.project.delivery.common.exception.DeliveryError.*;
import static com.sparta.project.delivery.common.type.UserRoleEnum.*;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final AddressRepository addressRepository;


    public Page<OrderDto> getOrdersByUser(UserDto userDto, Pageable pageable) {
        User user = userRepository.findById(userDto.userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if ( !user.getRole().equals(CUSTOMER) && !user.getRole().equals(MASTER) ) {
            throw new CustomException(AUTH_INVALID_CREDENTIALS);
        }

        return orderRepository.findAllByUser(user, pageable).map(OrderDto::from);
    }

    public Page<OrderDto> getOrdersByStore(UserDto userDto, String storeId, Pageable pageable) {
        User user = userRepository.findById(userDto.userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!user.getRole().equals(OWNER) && !user.getRole().equals(MASTER)) {
            throw new CustomException(AUTH_INVALID_CREDENTIALS);
        }

        Store store = storeRepository.findByStoreIdAndUser(storeId, user)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        return orderRepository.findAllByStore(store, pageable).map(OrderDto::from);
    }

    public OrderWithMenuDto getOrderByOrderId(UserDto userDto, String orderId) {
        User user = userRepository.findById(userDto.userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return switch (user.getRole()) {
            case CUSTOMER -> OrderWithMenuDto.from(
                    orderRepository.findByOrderIdAndUser(
                                    orderId, user
                            )
                            .orElseThrow(() -> new CustomException(ORDER_NOT_FOUND))
            );
            case OWNER -> OrderWithMenuDto.from(
                    orderRepository.findByOrderIdAndStore(
                                    orderId, storeRepository.findByUser(user).orElseThrow(() -> new CustomException(STORE_NOT_FOUND))
                            )
                            .orElseThrow(() -> new CustomException(ORDER_NOT_FOUND))
            );
            case MASTER, MANAGER -> OrderWithMenuDto.from(
                    orderRepository.findById(
                                    orderId
                            )
                            .orElseThrow(() -> new CustomException(ORDER_NOT_FOUND)));
        };
    }

    @Transactional
    public void createOrder(OrderDto orderDto, List<OrderMenuDto> orderMenuDtoList) {
        User user = userRepository.findById(orderDto.userDto().userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (user.getRole() != CUSTOMER) {
            throw new CustomException(AUTH_INVALID_CREDENTIALS);
        }

        // store
        Store store = storeRepository.findById(orderDto.storeId())
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        // address
        Address address = addressRepository.findByAddressIdAndUser(orderDto.addressId(), user)
                .orElseThrow(() -> new CustomException(ADDRESS_NOT_FOUND));

        // order
        Order order = orderDto.toEntity(user, store, address);
        orderRepository.save(order);

        //orderMenu
        Long totalPrice = 0L;
        List<OrderMenu> orderMenuList = new ArrayList<>();
        for (OrderMenuDto orderMenuDto : orderMenuDtoList) {
            Menu menu = menuRepository.findById(orderMenuDto.menuId()).
                    orElseThrow(() -> new CustomException(MENU_NOT_FOUND));
            totalPrice += menu.getPrice();
            orderMenuList.add(orderMenuDto.of(order, menu));
        }

        if (!totalPrice.equals(order.getPrice())) {
            throw new CustomException(ORDER_PRICE_NOT_SAME);
        }

        orderMenuRepository.saveAll(orderMenuList);
    }

    @Transactional
    public void approveOrder(UserDto userDto, String orderId) {
        User user = userRepository.findById(userDto.userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (user.getRole() != OWNER) {
            throw new CustomException(AUTH_INVALID_CREDENTIALS);
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));

        Store store = storeRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        if (order.getStore() != store) {
            throw new CustomException(STORE_IS_NOT_USER);
        }

        order.setStatus(OrderStatus.ORDER_COMPLETED);
    }

    @Transactional
    public void cancelOrder(UserDto userDto, String orderId) {
        User user = userRepository.findById(userDto.userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (user.getRole() != OWNER) {
            throw new CustomException(AUTH_INVALID_CREDENTIALS);
        }

        Order order = orderRepository.findById(orderId).orElseThrow();

        Store store = storeRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));


        if (order.getStore() != store) {
            throw new CustomException(STORE_IS_NOT_USER);
        }

        LocalDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        LocalDateTime orderTime = order.getCreatedAt();

        if (orderTime.plusMinutes(5).isBefore(now)) {
            throw new CustomException(ORDER_CANCEL_FAILED_TIMEOUT);
        }

        order.setStatus(OrderStatus.ORDER_CANCELLED);
    }
}
