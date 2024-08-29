package com.sparta.project.delivery.menu.repository;

import com.sparta.project.delivery.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, String> {
    // queryDSL 적용 후 impl 에서 매핑 해서 반환
    Page<Menu> findAllByStore_StoreId(String storeId, Pageable pageable);
    Menu findByStore_StoreIdAndMenuId(String storeId,String menuId);
}
