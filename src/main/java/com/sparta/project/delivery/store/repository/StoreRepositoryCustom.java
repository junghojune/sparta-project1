package com.sparta.project.delivery.store.repository;

import com.sparta.project.delivery.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryCustom {
    Page<Store> searchStore(String regionId, String categoryId, Pageable pageable);
}
