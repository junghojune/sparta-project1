package com.sparta.project.delivery.store.repository;

import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.store.constant.StoreSearchType;
import com.sparta.project.delivery.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryCustom {
    Page<Store> searchStore(String regionId,
                            String categoryId,
                            StoreSearchType searchType,
                            String searchValue,
                            UserDetailsImpl userDetails,
                            Pageable pageable);
}
