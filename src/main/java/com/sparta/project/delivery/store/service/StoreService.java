package com.sparta.project.delivery.store.service;


import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.category.entity.Category;
import com.sparta.project.delivery.category.repository.CategoryRepository;
import com.sparta.project.delivery.common.exception.CustomException;
import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.notice.dto.NoticeDto;
import com.sparta.project.delivery.region.entity.Region;
import com.sparta.project.delivery.region.repository.RegionRepository;
import com.sparta.project.delivery.store.constant.StoreSearchType;
import com.sparta.project.delivery.store.dto.StoreDto;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.store.repository.StoreRepository;
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.sparta.project.delivery.common.exception.DeliveryError.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;
    private final CategoryRepository CategoryRepository;
    private final UserRepository userRepository;

    public void createStore(StoreDto storeDto, UserDetailsImpl userDetails) {
        // 연관 관계 저장을 위한 Region, Category 조회
        Region region = regionRepository.findById(storeDto.regionId()).orElseThrow(
                () -> new CustomException(REGION_NOT_FOUND));
        Category category = CategoryRepository.findById(storeDto.categoryId()).orElseThrow(
                () -> new CustomException(CATEGORY_NOT_FOUND));
        User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        // 권한 체크
        if (user.getRole() == UserRoleEnum.CUSTOMER) {
            throw new CustomException(INVALID_ROLE);
        }
        //Store 저장
        storeRepository.save(storeDto.toEntity(user, region, category));
    }


    @Transactional(readOnly = true)
    public StoreDto getStore(String storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
        return StoreDto.from(store);
    }


    @Transactional(readOnly = true)
    public Page<StoreDto> getAllStores(String regionId, String categoryId,
                                       StoreSearchType searchType, String searchValue, Pageable pageable) {

        return storeRepository.searchStore(regionId, categoryId,searchType, searchValue, pageable).map(StoreDto::from);
    }

    public StoreDto updateStore(String storeId, StoreDto dto, UserDetailsImpl userDetails) {
        // 유저 권한 체크
        User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        // 식당 주인과 매니저만 변경 가능
        if (user.getRole().equals(UserRoleEnum.CUSTOMER) || user.getRole().equals(UserRoleEnum.MASTER)) {
            throw new CustomException(INVALID_ROLE);
        }
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
        //업데이트 내용 수정
        // dto.address() -> address 는 region 에 변화가 있을 때 수정?
        store.setName(dto.name());
        return StoreDto.from(storeRepository.save(store));
    }

    @Transactional
    // Soft delete -> isDeleted, deletedAt, deletedBy 수정
    public void deleteStore(String storeId, UserDetailsImpl userDetails) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
        store.deleteStore(LocalDateTime.now(), userDetails.getEmail());
    }

}
