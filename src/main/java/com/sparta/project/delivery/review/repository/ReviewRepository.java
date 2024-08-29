package com.sparta.project.delivery.review.repository;

import com.sparta.project.delivery.review.entity.Review;
import com.sparta.project.delivery.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Page<Review> findAllByStoreAndReportFlag(Store store, boolean reportFlag, Pageable pageable);
}
