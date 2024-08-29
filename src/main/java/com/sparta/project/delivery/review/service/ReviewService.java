package com.sparta.project.delivery.review.service;

import com.sparta.project.delivery.order.entity.Order;
import com.sparta.project.delivery.order.repository.OrderRepository;
import com.sparta.project.delivery.review.dto.ReviewDto;
import com.sparta.project.delivery.review.dto.ReviewReportDto;
import com.sparta.project.delivery.review.entity.Review;
import com.sparta.project.delivery.review.entity.ReviewReport;
import com.sparta.project.delivery.review.repository.ReviewReportRepository;
import com.sparta.project.delivery.review.repository.ReviewRepository;
import com.sparta.project.delivery.store.entity.Store;
import com.sparta.project.delivery.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewReportRepository reviewReportRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void addReview(ReviewDto dto) {
        Order order = orderRepository.findById(dto.orderId()).orElseThrow();

        Review review = dto.toEntity(dto.user(), order, order.getStore());
        reviewRepository.save(review);
    }

    public Page<ReviewDto> getReviewsByStoreId(String storeId, Pageable pageable) {
        Store store = storeRepository.findById(storeId).orElseThrow();
        Page<Review> reviews = reviewRepository.findAllByStoreAndReportFlag(store, false, pageable);

        return reviews.map(ReviewDto::from);
    }

    @Transactional
    public void deleteReview(String reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        reviewRepository.delete(review);
    }

    @Transactional
    public void reportReview(ReviewReportDto dto) {
        Review review = reviewRepository.findById(dto.reviewId()).orElseThrow();
        ReviewReport reviewReport = dto.toEntity(dto.user(), review);

        reviewReportRepository.save(reviewReport);

        review.setReportFlag(true);
    }
}
