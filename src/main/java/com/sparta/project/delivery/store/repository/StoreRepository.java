package com.sparta.project.delivery.store.repository;

import com.sparta.project.delivery.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, String>, StoreRepositoryCustom {


}
