package com.sparta.project.delivery.store.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.menu.entity.QMenu;
import com.sparta.project.delivery.store.constant.StoreSearchType;
import com.sparta.project.delivery.store.entity.QStore;
import com.sparta.project.delivery.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;


@RequiredArgsConstructor
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    QStore store = QStore.store;
    QMenu menu = QMenu.menu;

    @Override
    public Page<Store> searchStore(String regionId, String categoryId, StoreSearchType searchType, String searchValue, UserDetailsImpl userDetails, Pageable pageable) {
        BooleanExpression regionCondition = regionId != null ? store.region.regionId.eq(regionId) : null;
        BooleanExpression categoryCondition = categoryId != null ? store.category.categoryId.eq(categoryId) : null;

        // 입력 검색 조건
        BooleanExpression searchCondition = getSearchCondition(searchType, searchValue);
        BooleanExpression visibilityCondition = getVisibilityByUserRoleCondition(userDetails);

        JPAQuery<Store> query = queryFactory.selectFrom(store)
                .where(regionCondition, categoryCondition, visibilityCondition, searchCondition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // 메뉴 이름으로 검색할 때만 조인 추가
        if (searchType == StoreSearchType.MENU) {
            query.leftJoin(store.menus, menu);
        }

        List<Store> stores = query.fetch();

        return new PageImpl<>(stores, pageable, stores.size());
    }

    // UserRole에 따른 조건 설정
    private BooleanExpression getVisibilityByUserRoleCondition(UserDetailsImpl userDetails) {
        if (userDetails.getUser().getRole() == UserRoleEnum.MASTER) {
            // MASTER 권한인 경우 모든 Store를 조회
            return null;
        }
        // 일반 사용자 권한인 경우 필터링 조건 적용
        return store.isPublic.isTrue().and(store.isDeleted.isFalse());
    }

    private BooleanExpression getSearchCondition(StoreSearchType searchType, String searchValue) {
        if (searchValue == null || searchType == null) {
            return null;
        }
        return switch (searchType) {
            case NAME -> store.name.containsIgnoreCase(searchValue);
            case DESCRIPTION -> store.description.containsIgnoreCase(searchValue);
            case MENU -> menu.name.containsIgnoreCase(searchValue);
        };
    }
}
