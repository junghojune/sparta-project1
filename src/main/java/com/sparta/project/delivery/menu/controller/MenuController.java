package com.sparta.project.delivery.menu.controller;

import com.sparta.project.delivery.menu.dto.request.CreateMenu;
import com.sparta.project.delivery.menu.dto.request.UpdateMenu;
import com.sparta.project.delivery.menu.dto.response.MenuResponse;
import com.sparta.project.delivery.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

//TODO : MenuResponse 작성 후 수정
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/{storeId}")
    public String create(@PathVariable("storeId") String storeId, @RequestBody CreateMenu request) {
        return menuService.createMenu(storeId, request.toDto());
    }


    @GetMapping("/{storeId}")
    public Page<MenuResponse> getAll(
            @PathVariable String storeId,
            @ParameterObject @PageableDefault(
                    size = 10, sort = {"createdAt", "updatedAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return menuService.getAllMenu(storeId, pageable).map(MenuResponse::from);
    }

    @GetMapping("/{storeId}/{menuId}")
    public MenuResponse getOne(@PathVariable String storeId, @PathVariable String menuId) {
        return MenuResponse.from(menuService.getMenu(storeId, menuId));
    }

    @PutMapping("/{storeId}/{menuId}")
    public MenuResponse update(@PathVariable String storeId,
                          @PathVariable String menuId,
                          @RequestBody UpdateMenu request
    ) {
        return MenuResponse.from(menuService.updateMenu(storeId, menuId, request.toDto()));
    }

    @DeleteMapping("/{storeId}/{menuId}")
    public String delete(@PathVariable String storeId, @PathVariable String menuId) {

        return menuService.deleteMenu(storeId, menuId);
    }
}
