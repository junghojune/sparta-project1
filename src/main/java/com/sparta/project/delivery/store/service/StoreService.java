package com.sparta.project.delivery.store.service;


import com.sparta.project.delivery.category.repository.CategoryRepository;
import com.sparta.project.delivery.store.dto.StoreDto;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.store.repository.StoreRepository;
import com.sparta.project.delivery.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
//    private final RegionRepository regionRepository;
    private final CategoryRepository CategoryRepository;

    public String createStore(StoreDto storeDto, User user){
        // 연관 관계 저장을 위한 Region, Category 조회
        //Store 저장
//        Store store = storeRepository.save(storeDto.toEntity(user, region, category));
        return "Store created successfully";
    }


    // TODO : Response 의 형태 통일하도록 논의
    @Transactional(readOnly = true)
    public StoreDto getStore(String storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("store not found"));
        return StoreDto.from(store);
    }


    @Transactional(readOnly = true)
    public Page<StoreDto> getAllStores(String regionId, String categoryId, Pageable pageable) {
        //TODO : QueryDSL 적용 후 regionId 와 categoryId 에 대한 조건적 조회로 수정하기
        return storeRepository.findAll(pageable).map(StoreDto::from);
    }

    @Transactional
    public String updateStore(String storeId, StoreDto dto, User user) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("store not found"));
        //region, category 조회
        //업데이트 내용 검증 및 수정
        // dto.address() -> address 는 region 에 변화가 있을 때 수정?
        if (dto.name() != null && !dto.name().isEmpty()){
            store.setName(dto.name());
        }
        return "updated success";
    }

    @Transactional
    // Soft delete -> isDeleted, deletedAt, deletedBy 수정
    public String deleteStore(String storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("store not found"));
        store.setIsPublic(false);
        store.setIsDeleted(true);
        store.setDeletedAt(LocalDateTime.now());
        store.setDeletedBy("Owner");
        return "deleted success";
    }


}
