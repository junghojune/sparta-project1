package com.sparta.project.delivery.store.controller;


import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.response.CommonResponse;
import com.sparta.project.delivery.store.dto.request.CreateStore;
import com.sparta.project.delivery.store.dto.request.UpdateStore;
import com.sparta.project.delivery.store.dto.response.StoreResponse;
import com.sparta.project.delivery.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RestController()
@RequestMapping("/api/stores")
@Tag(name = "Store API", description = "음식점을 추가/수정/조회/삭제 할 수 있는 API 입니다.")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    @Operation(summary = "음식적 생성 API", description = "새 Store 를 생성 및 저장합니다.")
    public CommonResponse<Void> create(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateStore request) {
        storeService.createStore(request.toDto(), userDetails);
        return CommonResponse.success("음식점 생성 성공");
    }


    // Store 단일 조회
    @GetMapping("/{storeId}")
    @Operation(summary = "음식적 단건 조회 API", description = "ID 와 일치하는 Store 를 조회합니다.")
    public CommonResponse<StoreResponse> getOne(@PathVariable(name = "storeId") String storeId) {
        return CommonResponse.success(StoreResponse.from(storeService.getStore(storeId)));
    }

    @GetMapping
    @Operation(summary = "음식적 전체 조회 API", description = "전체 Store 를 조회합니다. 지역/카테고리는 선택적으로 필터링 가능")
    public CommonResponse<Page<StoreResponse>> getAll(
            @Parameter(name = "regionId", description = "지역 ID(검색용)", allowEmptyValue = true)
            @RequestParam(name = "regionId", required = false) String regionId,
            @Parameter(name = "categoryId", description = "카테고리 ID(검색용)", allowEmptyValue = true)
            @RequestParam(name = "categoryId", required = false) String categoryId,
            @ParameterObject @PageableDefault(
                    size = 10, sort = {"createdAt", "updatedAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return CommonResponse.success(
                storeService.getAllStores(regionId, categoryId, pageable).map(StoreResponse::from)
        );
    }

    @PutMapping("/{storeId}")
    @Operation(summary = "음식적 수정 API", description = "Store 내용을 수정합니다.(이름, 주소, 공개 여부)")
    public CommonResponse<StoreResponse> update(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "storeId") String storeId, @RequestBody UpdateStore request) {

        return CommonResponse.success(StoreResponse.from(storeService.updateStore(storeId, request.toDto(), userDetails)));
    }

    @DeleteMapping("/{storeId}")
    @Operation(summary = "음식적 삭제 API", description = "Store 를 삭제 합니다.")
    public CommonResponse<Void> delete(
            @PathVariable(name = "storeId") String storeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        storeService.deleteStore(storeId, userDetails);
        return CommonResponse.success("음식점 삭제 성공");
    }

}
