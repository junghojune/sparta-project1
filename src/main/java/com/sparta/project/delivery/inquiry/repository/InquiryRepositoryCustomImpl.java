package com.sparta.project.delivery.inquiry.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.project.delivery.common.exception.CustomException;
import com.sparta.project.delivery.common.exception.DeliveryError;
import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.inquiry.constant.InquirySearchType;
import com.sparta.project.delivery.inquiry.entity.Inquiry;
import com.sparta.project.delivery.inquiry.entity.QInquiry;
import com.sparta.project.delivery.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class InquiryRepositoryCustomImpl implements InquiryRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QInquiry inquiry = QInquiry.inquiry;

    @Override
    public Page<Inquiry> searchInquiry(InquirySearchType searchType, String searchValue, User user, Pageable pageable) {
        BooleanExpression roleCondition = getRoleCondition(user.getRole(), user.getEmail());
        BooleanExpression searchCondition = getSearchCondition(searchType, searchValue);

        List<Inquiry> inquiries = queryFactory.selectFrom(QInquiry.inquiry)
                .where(roleCondition, searchCondition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(inquiries, pageable, inquiries.size());

    }

    @Override
    public Inquiry findInquiryByIdByAuth(String inquiryId, User user) {
        BooleanExpression roleCondition = getRoleCondition(user.getRole(), user.getEmail());

        BooleanExpression idCondition = inquiry.inquiryId.eq(inquiryId);

        Inquiry result = queryFactory.selectFrom(inquiry)
                .where(idCondition, roleCondition)
                .fetchOne();

        if (result == null) {
            throw new CustomException(DeliveryError.INQUIRY_NOT_FOUND); // 정의된 예외 처리
        }

        return result;
    }


    private BooleanExpression getRoleCondition(UserRoleEnum userRole, String email) {
        if (userRole == UserRoleEnum.CUSTOMER) {
            return inquiry.user.email.eq(email);
        } else if (userRole == UserRoleEnum.MASTER) {
            return null; // MASTER 권한은 모든 데이터를 볼 수 있음
        }
        return null; // 다른 역할에 대해 기본값으로 null 반환
    }

    private BooleanExpression getSearchCondition(InquirySearchType searchType, String searchValue) {
        if (searchValue == null || searchValue.isBlank()) {
            return null;
        }

        return switch (searchType) {
            case TITLE -> inquiry.title.containsIgnoreCase(searchValue);
            case CONTENT -> inquiry.content.contains(searchValue);
        };
    }
}
