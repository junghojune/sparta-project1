package com.sparta.project.delivery.store.service;

import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.category.entity.Category;
import com.sparta.project.delivery.category.repository.CategoryRepository;
import com.sparta.project.delivery.common.type.City;
import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.region.entity.Region;
import com.sparta.project.delivery.region.repository.RegionRepository;
import com.sparta.project.delivery.store.constant.StoreSearchType;
import com.sparta.project.delivery.store.dto.StoreDto;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.store.repository.StoreRepository;
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StoreService storeService;

    private UserDetailsImpl userDetails;
    private StoreDto storeDto;
    private Store store;
    private User user;
    private Region region;
    private Category category;

    @BeforeEach
    void setUp() {

        // 초기화
        user = User.builder().userId(1L).email("test@example.com").role(UserRoleEnum.OWNER).build();
        region = Region.builder().regionId("regionId-123").city(City.SEOUL).siGun("Gangnam").gu("Gangnam").village("Yeoksam").build();
        category = Category.builder().categoryId("categoryId-123").name("Food").build();
        store = Store.builder().storeId("storeId-123").user(user).region(region).category(category).name("Test Store").address("123 Test St").description("정말 맛있는 가게입니다.").build();
        storeDto = StoreDto.builder()
                .storeId(store.getStoreId())
                .userEmail(user.getEmail())
                .userName(user.getUsername())
                .regionId(region.getRegionId())
                .city(region.getCity())
                .siGun(region.getSiGun())
                .gu(region.getGu())
                .village(region.getVillage())
                .categoryId(category.getCategoryId())
                .categoryName(category.getName())
                .name(store.getName())
                .address(store.getAddress())
                .description(store.getDescription())
                .isPublic(true)
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .createdBy(user.getEmail())
                .updatedAt(LocalDateTime.now())
                .updatedBy(user.getEmail())
                .deletedAt(null)
                .deletedBy(null)
                .build();
        userDetails = new UserDetailsImpl(user);
    }

    @Test
    void 음식점_생성() {
        when(regionRepository.findById(anyString())).thenReturn(Optional.of(region));
        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(category));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        storeService.createStore(storeDto, userDetails);

        verify(storeRepository, times(1)).save(any(Store.class));
    }

    @Test
    void 음식점_단건_조회() {
        when(storeRepository.findById(anyString())).thenReturn(Optional.of(store));

        StoreDto result = storeService.getStore(store.getStoreId());

        assertNotNull(result);
        assertEquals(store.getStoreId(), result.storeId());
    }

    @Test
    void 음식점_전체_조회_MASTER() {
        User masterUser = User.builder().userId(2L).email("master@example.com").role(UserRoleEnum.MASTER).build();
        UserDetailsImpl userDetailsMaster = new UserDetailsImpl(masterUser);
        Page<Store> storePage = new PageImpl<>(List.of(store));
        when(storeRepository.searchStore(anyString(), anyString(), any(), anyString(),any(), any(Pageable.class))).thenReturn(storePage);

        Page<StoreDto> result = storeService.getAllStores(region.getRegionId(), category.getCategoryId(), StoreSearchType.NAME, "Test",userDetailsMaster, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void 음식점_전체_조회_OTHER_ROLES() {
        Page<Store> storePage = new PageImpl<>(List.of(store));
        when(storeRepository.searchStore(anyString(), anyString(), any(), anyString(),any(), any(Pageable.class))).thenReturn(storePage);

        Page<StoreDto> result = storeService.getAllStores(region.getRegionId(), category.getCategoryId(), StoreSearchType.NAME, "Test",userDetails, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void 음식점_이름AND설명_수정() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(storeRepository.findById(anyString())).thenReturn(Optional.of(store));
        when(storeRepository.save(any(Store.class))).thenReturn(store);

        StoreDto updatedStoreDto = StoreDto.builder().name("Updated Store").description("정말 노맛인 집입니다.").build();
        StoreDto result = storeService.updateStore(store.getStoreId(), updatedStoreDto, userDetails);

        assertEquals("Updated Store", result.name());
        assertEquals("정말 노맛인 집입니다.", result.description());
    }

    @Test
    void 음식점_삭제() {
        when(storeRepository.findById(anyString())).thenReturn(Optional.of(store));

        storeService.deleteStore(store.getStoreId(), userDetails);

        verify(storeRepository, times(1)).findById(anyString());
    }

}