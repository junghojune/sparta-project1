package com.sparta.project.delivery.review.repository;

import com.sparta.project.delivery.review.dto.ReviewDto;
import com.sparta.project.delivery.review.entity.Review;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Page<Review> findAllByStoreAndReportFlagAndIsDeletedFalse(Store store, boolean reportFlag, Pageable pageable);

    Optional<Review> findByReviewIdAndUser(String reviewId, User user);

    Page<Review> findAllByUserAndIsDeletedFalse(User user, Pageable pageable);
}
