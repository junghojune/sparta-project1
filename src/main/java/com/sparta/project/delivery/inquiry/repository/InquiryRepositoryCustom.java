package com.sparta.project.delivery.inquiry.repository;

import com.sparta.project.delivery.inquiry.constant.InquirySearchType;
import com.sparta.project.delivery.inquiry.entity.Inquiry;
import com.sparta.project.delivery.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryRepositoryCustom {
    Page<Inquiry> searchInquiry(InquirySearchType searchType, String searchValue, User user, Pageable pageable);

    Inquiry findInquiryByIdByAuth(String inquiryId, User user);
}
