package com.sparta.project.delivery.store.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    public Page<Store> searchStore(String regionId, String categoryId, Pageable pageable) {
        BooleanExpression regionCondition = regionId != null ? store.region.regionId.eq(regionId) : null;
        BooleanExpression categoryCondition = categoryId != null ? store.category.categoryId.eq(categoryId) : null;

        List<Store> stores = queryFactory.selectFrom(store)
                .where(regionCondition, categoryCondition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(stores, pageable, stores.size());
    }
}
