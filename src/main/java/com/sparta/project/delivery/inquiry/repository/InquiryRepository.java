package com.sparta.project.delivery.inquiry.repository;

import com.sparta.project.delivery.inquiry.entity.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, String> {

    Page<Inquiry> findByUser_Email(String userEmail, Pageable pageable);
    Page<Inquiry> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Inquiry> findByContentContaining(String content, Pageable pageable);
}
