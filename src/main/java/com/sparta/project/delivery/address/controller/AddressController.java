package com.sparta.project.delivery.address.controller;

import com.sparta.project.delivery.address.dto.request.CreateAddress;
import com.sparta.project.delivery.address.dto.request.UpdateAddress;
import com.sparta.project.delivery.address.dto.response.AddressResponse;
import com.sparta.project.delivery.address.service.AddressService;
import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.response.CommonResponse;
import com.sparta.project.delivery.store.dto.response.StoreResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @Operation(summary = "주소지 등록 API", description = "Address를 생성, 등록합니다.")
    public CommonResponse<Void> create(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @RequestBody CreateAddress request){
        return addressService.create(userDetails, request.toDto());
    }

    // 로그인 한 사용자의 모든 주소 확인 - 에러 발생
    @GetMapping
    @Operation(summary = "주소지 조회 API", description = "로그인 한 사용자의 Address를 조회합니다.")
    public Page<AddressResponse> getAll(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @ParameterObject @PageableDefault(
                    size = 10, sort = {"createdAt", "updatedAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return addressService.getAll(userDetails,pageable).map(AddressResponse::from);
    }

    @PutMapping("/{address_id}")
    @Operation(summary = "주소지 수정 API", description = "Address를 수정합니다.")
    public CommonResponse<AddressResponse> update(@PathVariable String address_id,
                             @RequestBody UpdateAddress request,
                             @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return addressService.update(address_id, request.toDto() ,userDetails);
    }

    @DeleteMapping("/{address_id}")
    @Operation(summary = "주소지 삭제 API", description = "Address를 삭제 (isDeleted = true) 합니다.")
    public CommonResponse<Void> delete(@PathVariable String address_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return addressService.delete(address_id, userDetails);
    }
}
