package com.sparta.project.delivery.review.controller;

import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.review.dto.ReviewReportRequest;
import com.sparta.project.delivery.review.dto.ReviewRequest;
import com.sparta.project.delivery.review.dto.ReviewResponse;
import com.sparta.project.delivery.review.service.ReviewService;
import com.sparta.project.delivery.user.User;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public String addReview(@RequestBody ReviewRequest request) {
        User user = User.builder()
                .userId(1L)
                .name("테스트")
                .email("test@gmail.com")
                .password("1234")
                .role(UserRoleEnum.CUSTOMER)
                .build();

        reviewService.addReview(request.toDto(user));

        return "review create successfully";
    }

    @GetMapping("/{storeId}")
    public Page<ReviewResponse> getReviewsByStoreId(
            @PathVariable("storeId") String storeId,
            @ParameterObject
            @PageableDefault(
                    size = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable) {
        return reviewService.getReviewsByStoreId(storeId, pageable).map(ReviewResponse::from);
    }

    @DeleteMapping("/{reviewId}")
    public String deleteReview(@PathVariable("reviewId") String reviewId) {
        reviewService.deleteReview(reviewId);
        return "review delete successfully";
    }

    @PostMapping("/{reviewId}")
    public String reportReview(@PathVariable("reviewId") String reviewId,
                               @RequestBody ReviewReportRequest request
    ) {
        User user = User.builder()
                .userId(1L)
                .name("테스트")
                .email("test@gmail.com")
                .password("1234")
                .role(UserRoleEnum.CUSTOMER)
                .build();
        reviewService.reportReview(request.toDto(user, reviewId));
        return "review report successfully";
    }
}
