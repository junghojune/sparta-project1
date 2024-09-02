package com.sparta.project.delivery.payment.controller;

import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.response.CommonResponse;
import com.sparta.project.delivery.payment.dto.PaymentRequest;
import com.sparta.project.delivery.payment.dto.PaymentResponse;
import com.sparta.project.delivery.payment.service.PaymentService;
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
@RequestMapping("/api/payment")
@Tag(name = "Payment API", description = "결제 관련 API 입니다.")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    @Operation(summary = "고객 order 조회 API", description = "고객의 order 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
    })
    public CommonResponse<Page<PaymentResponse>> getPaymentsByUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ParameterObject
            @PageableDefault(
                    size = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return CommonResponse.success(
                paymentService.getPaymentsByUser(
                                UserDto.from(userDetails.getUser()), pageable
                        )
                        .map(PaymentResponse::from)
        );
    }

    @GetMapping("/{paymentId}")
    @Operation(summary = "payment 단건 조회 API", description = "단건 payment 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "991", description = "결제 내역을 찾을 수 없습니다.", content = @Content)
    })
    public CommonResponse<PaymentResponse> getPayment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("paymentId") String paymentId
    ) {
        return CommonResponse.success(
                PaymentResponse.from(
                        paymentService.getPaymentByPaymentId(UserDto.from(userDetails.getUser()), paymentId)
                )
        );
    }

    @PostMapping
    @Operation(summary = "payment 생성 API", description = "결제를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "931", description = "주문을 찾을 수 없습니다.", content = @Content),
    })
    public CommonResponse<String> createOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody PaymentRequest request
    ) {

        paymentService.createPayment(request.toDto(UserDto.from(userDetails.getUser())));

        return CommonResponse.success("결제를 성공하였습니다.");
    }
}
