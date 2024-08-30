package com.sparta.project.delivery.inquiry.repository;

import com.sparta.project.delivery.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, String>, InquiryRepositoryCustom {
}
