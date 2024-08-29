package com.sparta.project.delivery.inquiry.controller;


import com.sparta.project.delivery.inquiry.constant.InquirySearchType;
import com.sparta.project.delivery.inquiry.dto.InquiryRequest;
import com.sparta.project.delivery.inquiry.dto.InquiryResponse;
import com.sparta.project.delivery.inquiry.dto.InquirySetReplyRequest;
import com.sparta.project.delivery.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


//TODO : 유저 권한 조건 추가
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    //신고자 OR 관리자 권한에 따른 로직 나누기
    @GetMapping
    public Page<InquiryResponse> getAll(
            @RequestParam(required = false) InquirySearchType searchType,
            @RequestParam(required = false) String searchValue,
            @ParameterObject @PageableDefault(
                    size = 10, sort = {"createdAt", "updatedAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable
            //            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return inquiryService.getAllInquiry(searchType, searchValue, pageable).map(InquiryResponse::from);
    }


    //신고자 OR 관리자 권한에 따른 로직 나누기
    @GetMapping("/{inquiryId}")
    public InquiryResponse getOne(
            //            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable String inquiryId) {
        return InquiryResponse.from(inquiryService.getInquiry(inquiryId));
    }

    //TODO : user 추가 , set status 접수 중
    @PostMapping
    public String create(
//            @AuthenticationPrincipal UserDetailsImpl userDetails
            @RequestBody InquiryRequest request
    ) {
//        return inquiryService.createInquiry(request.toDto(userDetails.getEmail()));
        return "create";
    }

    //신고 사항 수정 -> 제목, 내용
    @PutMapping("/{inquiryId}")
    public InquiryResponse update(@PathVariable String inquiryId, @RequestBody InquiryRequest request) {
        return InquiryResponse.from(inquiryService.updateInquiry(inquiryId, request.toDto()));
    }


    // 신고자만 삭제 가능
    @DeleteMapping("/{inquiryId}")
    public String delete(
            @PathVariable String inquiryId) {
        return inquiryService.deleteInquiry(inquiryId);
    }

    // TODO : 관리자만 사용 권한
    @PostMapping("/{inquiryId}")
    public String reply(
            @PathVariable String inquiryId,
            @RequestBody InquirySetReplyRequest request
    ){
        return inquiryService.replyToInquiry(inquiryId, request);
    }
}
