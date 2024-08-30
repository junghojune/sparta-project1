package com.sparta.project.delivery.order.controller;

import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.order.dto.OrderMenuRequest;
import com.sparta.project.delivery.order.dto.OrderRequest;
import com.sparta.project.delivery.order.dto.OrderResponse;
import com.sparta.project.delivery.order.dto.OrderWithMenuResponse;
import com.sparta.project.delivery.order.service.OrderService;
import com.sparta.project.delivery.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order API", description = "주문을 추가 수정 조회 할 수 있는 API 입니다.")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "order 조회 API", description = "고객의 order 정보를 조회합니다.")
    public Page<OrderResponse> getOrdersByUser(
            @ParameterObject
            @PageableDefault(
                    size = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        User user = User.builder()
                .userId(1L)
                .username("테스트")
                .email("test@gmail.com")
                .password("1234")
                .role(UserRoleEnum.CUSTOMER)
                .build();

        return orderService.getOrdersByUser(user, pageable).map(OrderResponse::from);
    }

    @GetMapping("/store/{storeId}")
    @Operation(summary = "order 조회 API", description = "가게의 order 정보를 조회합니다.")
    public Page<OrderResponse> getOrdersByStore(@PathVariable("storeId") String storeId,
                                                @ParameterObject
                                                @PageableDefault(
                                                        size = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC
                                                ) Pageable pageable
    ) {
        return orderService.getOrdersByStore(storeId, pageable).map(OrderResponse::from);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "order 조회 API", description = "단건 order 정보를 조회합니다.")
    public OrderWithMenuResponse getOrder(@PathVariable("orderId") String orderId) {
        return OrderWithMenuResponse.from(orderService.getOrderByOrderId(orderId));
    }

    @PostMapping
    @Operation(summary = "order 생성 API", description = "주문을 생성합니다.")
    public String createOrder(@RequestBody OrderRequest request) {
        // TODO : 로그인 기능 완료시 수정
        User user = User.builder()
                .userId(1L)
                .username("테스트")
                .email("test@gmail.com")
                .password("1234")
                .role(UserRoleEnum.CUSTOMER)
                .build();
        orderService.createOrder(request.toDto(user), request.menus().stream().map(OrderMenuRequest::toDto).toList());

        return "Order created successfully";
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "order 승인 API", description = "주문을 승인합니다.")
    public String approveOrder(@PathVariable("orderId") String orderId) {
        orderService.approveOrder(orderId);
        return "Order approved successfully";
    }

    @PutMapping("/{orderId}/cancel")
    @Operation(summary = "order 취소 API", description = "주문을 취소합니다.")
    public String cancelOrder(@PathVariable("orderId") String orderId) {
        orderService.cancelOrder(orderId);
        return "Order cancelled successfully";
    }
}