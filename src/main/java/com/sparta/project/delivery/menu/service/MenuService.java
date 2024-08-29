package com.sparta.project.delivery.menu.service;


import com.sparta.project.delivery.menu.dto.MenuDto;
import com.sparta.project.delivery.menu.entity.Menu;
import com.sparta.project.delivery.menu.repository.MenuRepository;

import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public String createMenu(String storeId, MenuDto dto) {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new IllegalArgumentException("ID 에 해당하는 store 가 없습니다.")
        );
        // TODO : validation 어노테이션을 통해 record 에서 검증
        if (dto.name() == null) {
            throw new IllegalArgumentException("Menu 이름이 필요합니다.");
        }
        if (dto.price() == null || dto.price() < 0) {
            throw new IllegalArgumentException("Menu 가격이 없거나 잘못되었습니다.");
        }
        if (dto.description() == null) {
            throw new IllegalArgumentException("Menu 설명이 필요합니다.");
        }
        menuRepository.save(dto.toEntity(store));
        return "Menu created";
    }

    @Transactional(readOnly = true)
    public Page<MenuDto> getAllMenu(String storeId, Pageable pageable) {
        return menuRepository.findAllByStore_StoreId(storeId, pageable)
                .map(MenuDto::from);
    }

    @Transactional(readOnly = true)
    public MenuDto getMenu(String storeId, String menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new IllegalArgumentException("ID 에 해당하는 store 가 없습니다."));
        if (!storeId.equals(menu.getStore().getStoreId())) {
            throw new IllegalArgumentException("요청의 storeId 와 menu 의 storeId 가 다릅니다.");
        }
        return MenuDto.from(menu);
    }

    @Transactional
    public MenuDto updateMenu(String storeId, String menuId, MenuDto dto) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new IllegalArgumentException("ID 에 해당하는 store 가 없습니다.")
        );
        if (!storeId.equals(menu.getStore().getStoreId())) {
            throw new IllegalArgumentException("요청의 storeId 와 menu 의 storeId 가 다릅니다.");
        }
        // TODO : validation 어노테이션을 통해 record 에서 검증
        menu.setName(dto.name());
        menu.setDescription(dto.description());
        menu.setPrice(dto.price());

        return MenuDto.from(menuRepository.save(menu));
    }

    @Transactional
    public String deleteMenu(String storeId, String menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new IllegalArgumentException("ID 에 해당하는 store 가 없습니다.")
        );
        if (!storeId.equals(menu.getStore().getStoreId())) {
            throw new IllegalArgumentException("요청의 storeId 와 menu 의 storeId 가 다릅니다.");
        }
        //TODO : 메소드로 분리하기
        menu.setIsDeleted(true);
        menu.setIsPublic(false);
        menu.setDeletedAt(LocalDateTime.now());
        menu.setDeletedBy("Owner");
        return "menu deleted";
    }
}
