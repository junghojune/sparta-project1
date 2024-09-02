package com.sparta.project.delivery.menu.service;


import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.exception.CustomException;
import com.sparta.project.delivery.common.exception.DeliveryError;
import com.sparta.project.delivery.menu.constant.MenuSearchType;
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

import static com.sparta.project.delivery.common.exception.DeliveryError.*;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public void createMenu(String storeId, MenuDto dto, UserDetailsImpl userDetails) {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new CustomException(STORE_NOT_FOUND)
        );
        checkStoreOwner(store, userDetails);

        menuRepository.save(dto.toEntity(store));
    }

    @Transactional(readOnly = true)
    public Page<MenuDto> getAllMenu(String storeId, MenuSearchType searchType, String searchValue, Pageable pageable) {
        if (searchValue == null || searchValue.isBlank()) {
            return menuRepository.findAllByStore_StoreId(storeId, pageable).map(MenuDto::from);
        }
        return switch (searchType) {
            case NAME ->
                    menuRepository.
                            findAllByStore_StoreIdAndNameContainsIgnoreCase(storeId, searchValue, pageable)
                            .map(MenuDto::from);
            case DESCRIPTION ->
                    menuRepository.
                            findAllByStore_StoreIdAndDescriptionContainingIgnoreCase(storeId, searchValue, pageable)
                            .map(MenuDto::from);
        };
    }

    @Transactional(readOnly = true)
    public MenuDto getMenu(String storeId, String menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new CustomException(MENU_NOT_FOUND));
        checkIsStoreMenu(storeId, menu);

        return MenuDto.from(menu);
    }

    @Transactional
    public MenuDto updateMenu(String storeId, String menuId, MenuDto dto, UserDetailsImpl userDetails) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new CustomException(MENU_NOT_FOUND)
        );
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new CustomException(STORE_NOT_FOUND)
        );
        //가게의 메뉴인지, 가게 주인이 맞는 지 검증
        checkIsStoreMenu(store.getStoreId(), menu);
        checkStoreOwner(store, userDetails);
        // 내용 수정
        menu.setName(dto.name());
        menu.setDescription(dto.description());
        menu.setPrice(dto.price());

        return MenuDto.from(menuRepository.save(menu));
    }

    @Transactional
    public void deleteMenu(String storeId, String menuId, UserDetailsImpl userDetails) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new CustomException(MENU_NOT_FOUND)
        );
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new CustomException(STORE_NOT_FOUND)
        );
        checkIsStoreMenu(store.getStoreId(), menu);
        checkStoreOwner(store, userDetails);
        // 삭제
        menu.deleteMenu(LocalDateTime.now(), userDetails.getEmail());
    }


    private void checkIsStoreMenu(String storeId, Menu menu){
        if (!storeId.equals(menu.getStore().getStoreId()))
            throw new CustomException(MENU_NOT_IN_STORE);

    }

    private void checkStoreOwner(Store store, UserDetailsImpl userDetails) {
        if (!store.getUser().getEmail().equals(userDetails.getEmail()))
            throw new CustomException(STORE_IS_NOT_USER);
    }
}
