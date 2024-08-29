package com.sparta.project.delivery.notice.controller;


import com.sparta.project.delivery.notice.constant.NoticeSearchType;
import com.sparta.project.delivery.notice.dto.NoticeRequest;
import com.sparta.project.delivery.notice.dto.NoticeResponse;
import com.sparta.project.delivery.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

//TODO : User 권한 추가해야함
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public Page<NoticeResponse> getAll(
            @RequestParam(required = false) NoticeSearchType searchType,
            @RequestParam(required = false) String searchValue,
            @ParameterObject @PageableDefault(
                    size = 10, sort = {"createdAt", "updatedAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return noticeService.getAllNotices(searchType, searchValue, pageable).map(NoticeResponse::from);
    }

    @GetMapping("/{noticeId}")
    public NoticeResponse getOne(@PathVariable String noticeId) {
        return NoticeResponse.from(noticeService.getNotice(noticeId));
    }

    @PostMapping
    public String create(@RequestBody NoticeRequest request) {
        return noticeService.createNotice(request.toDto());
    }

    @PutMapping("/{noticeId}")
    public NoticeResponse update(@PathVariable String noticeId, @RequestBody NoticeRequest request) {
        return NoticeResponse.from(noticeService.updateNotice(noticeId, request));
    }

    @DeleteMapping("/{noticeId}")
    public String delete(@PathVariable String noticeId) {
        return noticeService.deleteNotice(noticeId);
    }
}
