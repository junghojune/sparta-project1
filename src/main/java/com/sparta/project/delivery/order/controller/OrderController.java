package com.sparta.project.delivery.order.controller;

import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.response.CommonResponse;
import com.sparta.project.delivery.order.dto.OrderMenuRequest;
import com.sparta.project.delivery.order.dto.OrderRequest;
import com.sparta.project.delivery.order.dto.OrderResponse;
import com.sparta.project.delivery.order.dto.OrderWithMenuResponse;
import com.sparta.project.delivery.order.service.OrderService;
import com.sparta.project.delivery.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order API", description = "주문을 추가 수정 조회 할 수 있는 API 입니다.")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "고객 order 조회 API", description = "고객의 order 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
    })
    public CommonResponse<Page<OrderResponse>> getOrdersByUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ParameterObject
            @PageableDefault(
                    size = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return CommonResponse.success(
                orderService.getOrdersByUser(
                                UserDto.from(userDetails.getUser()), pageable
                        )
                        .map(OrderResponse::from)
        );
    }

    @GetMapping("/store/{storeId}")
    @Operation(summary = "주인 order 조회 API", description = "가게의 order 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "700", description = "가게를 찾을 수 없습니다.", content = @Content),
    })
    public CommonResponse<Page<OrderResponse>> getOrdersByStore(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("storeId") String storeId,
            @ParameterObject
            @PageableDefault(
                    size = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return CommonResponse.success(
                orderService.getOrdersByStore(
                                UserDto.from(userDetails.getUser()), storeId, pageable
                        )
                        .map(OrderResponse::from)
        );
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "order 단건 조회 API", description = "단건 order 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "700", description = "가게를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "901", description = "주문을 찾을 수 없습니다.", content = @Content),
    })
    public CommonResponse<OrderWithMenuResponse> getOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("orderId") String orderId
    ) {
        return CommonResponse.success(
                OrderWithMenuResponse.from(
                        orderService.getOrderByOrderId(UserDto.from(userDetails.getUser()), orderId)
                )
        );
    }

    @PostMapping
    @Operation(summary = "order 생성 API", description = "주문을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "배송지를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "메뉴를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "905", description = "주문 가격이 실제 가격과 다릅니다.", content = @Content),
    })
    public CommonResponse<String> createOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody OrderRequest request
    ) {

        orderService.createOrder(
                request.toDto(UserDto.from(userDetails.getUser())),
                request.menus().stream().map(OrderMenuRequest::toDto).toList()
        );

        return CommonResponse.success("주문을 성공하였습니다.");
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "order 승인 API", description = "주문을 승인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "704", description = "가게의 주인이 아닙니다.", content = @Content)
    })
    public CommonResponse<String> approveOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("orderId") String orderId
    ) {
        orderService.approveOrder(UserDto.from(userDetails.getUser()), orderId);
        return CommonResponse.success("주문이 승인되었습니다.");
    }

    @PutMapping("/{orderId}/cancel")
    @Operation(summary = "order 취소 API", description = "주문을 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "704", description = "가게의 주인이 아닙니다.", content = @Content),
            @ApiResponse(responseCode = "904", description = "주문 취소 시간이 지났습니다.", content = @Content)
    })
    public CommonResponse<String> cancelOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("orderId") String orderId
    ) {
        orderService.cancelOrder(UserDto.from(userDetails.getUser()), orderId);
        return CommonResponse.success("주문이 취소되었습니다.");
    }
}