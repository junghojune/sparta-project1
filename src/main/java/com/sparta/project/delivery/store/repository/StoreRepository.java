package com.sparta.project.delivery.store.repository;

import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, String>, StoreRepositoryCustom {
    Optional<Store> findByStoreIdAndUser(String storeId, User user);

    Optional<Store> findByUser(User user);

}
