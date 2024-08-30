package com.sparta.project.delivery.store.controller;


import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.store.service.StoreService;
import com.sparta.project.delivery.store.dto.StoreDto;
import com.sparta.project.delivery.store.dto.request.UpdateStore;
import com.sparta.project.delivery.store.dto.request.CreateStore;
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
@RestController()
@RequestMapping("/api/stores")
@Tag(name = "Store API", description = "음식점을 추가/수정/조회/삭제 할 수 있는 API 입니다.")
public class StoreController {

    private final StoreService storeService;

    // TODO : common response 협의 후 변경하기
    @PostMapping
    @Operation(summary = "음식적 생성 API", description = "새 Store 를 생성 및 저장합니다.")
    public String create(@RequestBody CreateStore request) {
        // TODO : security 작성 후 수정
        User user = User.builder().userId(1L).email("owner@mail.com").username("temporaryOwner")
                .role(UserRoleEnum.OWNER).password("12345").build();
        return storeService.createStore(request.toDto(), user);
    };

    // Store 단일 조회
    @GetMapping("/{storeId}")
    @Operation(summary = "음식적 단건 조회 API", description = "ID 와 일치하는 Store 를 조회합니다.")
    public StoreDto getOne(@PathVariable String storeId){
        return storeService.getStore(storeId);
    }

    @GetMapping
    @Operation(summary = "음식적 전체 조회 API", description = "전체 Store 를 조회합니다. 지역/카테고리는 선택적으로 필터링 가능")
    public Page<StoreDto> getAll(
            @RequestParam(name = "regionId", required = false) String regionId,
            @RequestParam(name = "categoryId", required = false) String categoryId,
            @ParameterObject @PageableDefault(
                    size = 10, sort = {"createdAt", "updatedAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable
    ){
        return storeService.getAllStores(regionId, categoryId, pageable);
    }


    @PutMapping("/{storeId}")
    @Operation(summary = "음식적 수정 API", description = "Store 내용을 수정합니다.(이름, 주소)")
    public String update(@PathVariable String storeId, @RequestBody UpdateStore request){
        // TODO : security 작성 후 수정
        User user = User.builder().userId(1L).email("owner@mail.com").username("temporaryOwner")
                .role(UserRoleEnum.OWNER).password("12345").build();
        return storeService.updateStore(storeId, request.toDto(), user);

    }

    @DeleteMapping("/{storeId}")
    @Operation(summary = "음식적 삭제 API", description = "Store 를 삭제 합니다.")
    public String delete(@PathVariable String storeId){
        return storeService.deleteStore(storeId);
    }


}
