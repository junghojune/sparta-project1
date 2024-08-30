package com.sparta.project.delivery.store.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

    @Override
    public Page<Store> searchStore(String regionId, String categoryId, StoreSearchType searchType, String searchValue, Pageable pageable) {
        //지역 or 카테고리 버튼 입력 시 추가되는 조건
        BooleanExpression regionCondition = regionId != null ? store.region.regionId.eq(regionId) : null;
        BooleanExpression categoryCondition = categoryId != null ? store.category.categoryId.eq(categoryId) : null;

        // 입력 검색 조건
        BooleanExpression searchCondition = getSearchCondition(searchType, searchValue);

        List<Store> stores = queryFactory.selectFrom(store)
                .where(regionCondition, categoryCondition, searchCondition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(stores, pageable, stores.size());
    }



    private BooleanExpression getSearchCondition(StoreSearchType searchType, String searchValue) {
        if (searchValue == null || searchType == null) {
            return null;
        }
        return switch (searchType) {
            case NAME -> store.name.containsIgnoreCase(searchValue);
            // 추후 다른 검색 타입 추가
        };
    }
}