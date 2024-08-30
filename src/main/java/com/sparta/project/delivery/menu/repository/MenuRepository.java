package com.sparta.project.delivery.menu.repository;

import com.sparta.project.delivery.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, String> {
    Page<Menu> findAllByStore_StoreId(String storeId, Pageable pageable);
    Page<Menu> findAllByStore_StoreIdAndNameContainsIgnoreCase(String storeId, String searchValue, Pageable pageable);
    Page<Menu> findAllByStore_StoreIdAndDescriptionContainingIgnoreCase(String storeId, String searchValue, Pageable pageable);

}
