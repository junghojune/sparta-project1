package com.sparta.project.delivery.review.service;

import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.exception.CustomException;
import com.sparta.project.delivery.common.type.UserRoleEnum;
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
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.dto.UserDto;
import com.sparta.project.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.project.delivery.common.exception.DeliveryError.*;
import static com.sparta.project.delivery.common.type.UserRoleEnum.*;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewReportRepository reviewReportRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addReview(ReviewDto dto) {
        User user = userRepository.findById(dto.userDto().userId()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Order order = orderRepository.findByOrderIdAndUser(dto.orderId(), user).orElseThrow(() -> new CustomException(REVIEW_CREATION_FAILED_NOT_ORDER));

        Review review = dto.toEntity(user, order, order.getStore());
        Store store = review.getStore();
        updateStoreRatingAndReviewCount(store, review.getRating());

        reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public Page<ReviewDto> getReviewsByStoreId(String storeId, Pageable pageable) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        Page<Review> reviews = reviewRepository.findAllByStoreAndReportFlagAndIsDeletedFalse(store, false, pageable);

        return reviews.map(ReviewDto::from);
    }

    @Transactional
    public void deleteReview(UserDto userDto, String reviewId) {
        User user = userRepository.findById(userDto.userId()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Review review = reviewRepository.findByReviewIdAndUser(reviewId, user).orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        if (review.getIsDeleted()) {
            throw new CustomException(ALREADY_DELETED_REVIEW);
        }

        review.setIsDeleted(true);
    }

    @Transactional
    public void reportReview(ReviewReportDto dto) {
        User user = userRepository.findById(dto.userDto().userId()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Review review = reviewRepository.findById(dto.reviewId()).orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        if (review.getReportFlag()) {
            throw new CustomException(ALREADY_REPORTED_REVIEW);
        }
        ReviewReport reviewReport = dto.toEntity(user, review);

        reviewReportRepository.save(reviewReport);

        review.setReportFlag(true);
    }

    public Page<ReviewDto> getAllReviewsByUser(Long userId,Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return reviewRepository.findAllByUserAndIsDeletedFalse(user, pageable).map(ReviewDto::from);

    }

    private void updateStoreRatingAndReviewCount(Store store, int newRating) {
        int currentReviewCount = store.getReviewCount();
        float currentAverageRating = store.getAverageRating();

        // 새 평균평점 계산
        float updatedAverageRating = (currentAverageRating * currentReviewCount + newRating) / (currentReviewCount + 1);

        // 업데이트
        store.setAverageRating(updatedAverageRating);
        store.setReviewCount(currentReviewCount + 1);

        storeRepository.save(store);
    }

}
