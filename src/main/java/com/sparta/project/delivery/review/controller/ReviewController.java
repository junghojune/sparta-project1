package com.sparta.project.delivery.review.controller;

import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.response.CommonResponse;
import com.sparta.project.delivery.review.dto.ReviewReportRequest;
import com.sparta.project.delivery.review.dto.ReviewRequest;
import com.sparta.project.delivery.review.dto.ReviewResponse;
import com.sparta.project.delivery.review.dto.UserAllReviewResponse;
import com.sparta.project.delivery.review.service.ReviewService;
import com.sparta.project.delivery.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "review 생성 API", description = "고객이 리뷰를 생성합니다..")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "961", description = "주문한 이력이 없습니다.", content = @Content),
    })
    public CommonResponse<String> addReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewRequest request) {
        reviewService.addReview(request.toDto(UserDto.from(userDetails.getUser())));

        return CommonResponse.success("리뷰 작성이 완료되었습니다.");
    }

    @GetMapping()
    @Operation(summary = "유저의 모든 리뷰 조회", description = "유저의 모든 review 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.", content = @Content),
    })
    public CommonResponse<Page<UserAllReviewResponse>> getReviewsByUser(
            @RequestParam(value = "userId") Long userId,
            @ParameterObject
            @PageableDefault(
                    size = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return CommonResponse.success(
                reviewService.getAllReviewsByUser(userId, pageable)
                        .map(UserAllReviewResponse::from)
        );
    }

    @GetMapping("/{storeId}")
    @Operation(summary = "가게 review 조회 API", description = "가게의 review 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "700", description = "가게를 찾을 수 없습니다.", content = @Content),
    })
    public CommonResponse<Page<ReviewResponse>> getReviewsByStoreId(
            @PathVariable("storeId") String storeId,
            @ParameterObject
            @PageableDefault(
                    size = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable) {
        return CommonResponse.success(
                reviewService.getReviewsByStoreId(storeId, pageable)
                        .map(ReviewResponse::from)
        );
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "review 삭제 API", description = "자신이 작성한 리뷰를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "961", description = "사용자의 리뷰가 아닙니다.", content = @Content),
            @ApiResponse(responseCode = "966", description = "이미 삭제된 리뷰입니다.", content = @Content),
    })
    public CommonResponse<String> deleteReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("reviewId") String reviewId) {
        reviewService.deleteReview(UserDto.from(userDetails.getUser()), reviewId);
        return CommonResponse.success("리뷰 삭제를 성공했습니다.");
    }

    @PostMapping("/{reviewId}")
    @Operation(summary = "리뷰 신고 API", description = "리뷰를 신고하는 기능입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "961", description = "리뷰의 정보가 없습니다.", content = @Content),
            @ApiResponse(responseCode = "965", description = "이미 신고가 된 리뷰입니다.", content = @Content),
    })
    public CommonResponse<String> reportReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("reviewId") String reviewId,
            @RequestBody ReviewReportRequest request
    ) {
        reviewService.reportReview(request.toDto(UserDto.from(userDetails.getUser()), reviewId));
        return CommonResponse.success("리뷰가 신고되었습니다.");
    }
}
