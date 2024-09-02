package com.sparta.project.delivery.notice.repository;

import com.sparta.project.delivery.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, String> {
    Page<Notice> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Notice> findByContentContaining(String title, Pageable pageable);

}
