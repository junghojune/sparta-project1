package com.sparta.project.delivery.menu.controller;

import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.response.CommonResponse;
import com.sparta.project.delivery.menu.constant.MenuSearchType;
import com.sparta.project.delivery.menu.dto.request.CreateMenu;
import com.sparta.project.delivery.menu.dto.request.UpdateMenu;
import com.sparta.project.delivery.menu.dto.response.MenuResponse;
import com.sparta.project.delivery.menu.service.MenuService;
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
@RestController
@RequestMapping("/api/menus")
@Tag(name = "Menu API", description = "메뉴를 추가/수정/조회/삭제 할 수 있는 API 입니다.")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/{storeId}")
    @Operation(summary = "메뉴 생성", description = "가게의 메뉴를 생성하는 API 입니다.")
    public CommonResponse<Void> create(
            @PathVariable String storeId,
            @RequestBody CreateMenu request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        menuService.createMenu(storeId, request.toDto(), userDetails);
        return CommonResponse.success("메뉴 생성 성공");
    }


    @GetMapping("/{storeId}")
    @Operation(summary = "모든 메뉴 조회", description = "가게의 모든 메뉴를 조회하는 API 입니다.( 이름/설명 검색 )")
    public CommonResponse<Page<MenuResponse>> getAll(
            @PathVariable String storeId,
            @Parameter(name = "searchType", description = "검색하고자 하는 필드")
            @RequestParam(required = false, name = "searchType") MenuSearchType searchType,
            @Parameter(name = "searchValue", description = "검색 키워드 - 검색 타입에 맞게 검색")
            @RequestParam(required = false, name = "searchValue") String searchValue,
            @ParameterObject @PageableDefault(
                    size = 10, sort = {"createdAt", "updatedAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return CommonResponse.success(
                menuService.getAllMenu(storeId, searchType, searchValue, pageable).map(MenuResponse::from)
        );
    }

    @GetMapping("/{storeId}/{menuId}")
    @Operation(summary = "메뉴 단건 조회", description = "가게의 메뉴를 상세 조회하는 API 입니다.")
    public CommonResponse<MenuResponse> getOne(@PathVariable String storeId, @PathVariable String menuId) {
        return CommonResponse.success(MenuResponse.from(menuService.getMenu(storeId, menuId)));
    }

    @PutMapping("/{storeId}/{menuId}")
    @Operation(summary = "메뉴 수정", description = "가게의 메뉴를 수정하는 API 입니다.")
    public CommonResponse<MenuResponse> update(@PathVariable String storeId,
                               @PathVariable String menuId,
                               @RequestBody UpdateMenu request,
                               @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return CommonResponse.success(
                MenuResponse.from(menuService.updateMenu(storeId, menuId, request.toDto(), userDetails))
        );
    }

    @DeleteMapping("/{storeId}/{menuId}")
    @Operation(summary = "메뉴 삭제", description = "가게의 메뉴를 삭제하는 API 입니다.")
    public CommonResponse<Void> delete(
            @PathVariable String storeId,
            @PathVariable String menuId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        menuService.deleteMenu(storeId, menuId, userDetails);
        return CommonResponse.success("메뉴 삭제 완료");
    }
}
