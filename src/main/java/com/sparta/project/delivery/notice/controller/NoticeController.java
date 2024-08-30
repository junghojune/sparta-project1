package com.sparta.project.delivery.notice.controller;


import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.response.CommonResponse;
import com.sparta.project.delivery.notice.constant.NoticeSearchType;
import com.sparta.project.delivery.notice.dto.NoticeRequest;
import com.sparta.project.delivery.notice.dto.NoticeResponse;
import com.sparta.project.delivery.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/notice")
@Tag(name = "Notice API", description = "공지사항을 추가/수정/조회/삭제 할 수 있는 API 입니다.")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    @Operation(summary = "모든 공지사항 조회", description = "모든 공지사항 정보를 조회하는 API 입니다.(제목/내용 검색)")
    public CommonResponse<Page<NoticeResponse>> getAll(
            @Parameter(name = "searchType",description = "검색하고자 하는 필드")
            @RequestParam(required = false,name = "searchType") NoticeSearchType searchType,
            @Parameter(name = "searchValue",description = "검색 키워드 - 검색 타입에 맞게 검색")
            @RequestParam(required = false, name = "searchValue") String searchValue,
            @ParameterObject @PageableDefault(
                    size = 10, sort = {"createdAt", "updatedAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return CommonResponse.success(
                noticeService.getAllNotices(searchType, searchValue, pageable).map(NoticeResponse::from)
        );
    }

    @GetMapping("/{noticeId}")
    @Operation(summary = "공지사항 단건 조회", description = "공지사항 단건 조회 API 입니다.")
    public CommonResponse<NoticeResponse> getOne(@PathVariable String noticeId) {
        return CommonResponse.success(NoticeResponse.from(noticeService.getNotice(noticeId)));
    }

    @PostMapping
    @Operation(summary = "공지사항 생성", description = "공지사항을 생성하는 API 입니다. (관리자/매니저 권한)")
    public CommonResponse<Void> create(@RequestBody NoticeRequest request) {
        noticeService.createNotice(request.toDto());
        return CommonResponse.success("공지사항 생성 완료");
    }

    @PutMapping("/{noticeId}")
    @Operation(summary = "공지사항 수정", description = "공지사항을 수정하는 API 입니다. (관리자/매니저 권한)")
    public CommonResponse<NoticeResponse> update(@PathVariable String noticeId, @RequestBody NoticeRequest request) {
        return CommonResponse.success(NoticeResponse.from(noticeService.updateNotice(noticeId, request)));
    }

    @DeleteMapping("/{noticeId}")
    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제하는 API 입니다. (관리자/매니저 권한)")
    public CommonResponse<Void> delete(
            @PathVariable String noticeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        noticeService.deleteNotice(noticeId, userDetails);
        return CommonResponse.success("공지사항 삭제 완료");
    }
}
