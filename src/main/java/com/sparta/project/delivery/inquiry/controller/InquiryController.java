package com.sparta.project.delivery.inquiry.controller;


import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.response.CommonResponse;
import com.sparta.project.delivery.inquiry.constant.InquirySearchType;
import com.sparta.project.delivery.inquiry.dto.InquiryRequest;
import com.sparta.project.delivery.inquiry.dto.InquiryResponse;
import com.sparta.project.delivery.inquiry.dto.InquirySetReplyRequest;
import com.sparta.project.delivery.inquiry.service.InquiryService;
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
@RequestMapping("/api/inquiry")
@Tag(name = "Inquiry API", description = "고객센터 신고를 추가/수정/조회/삭제 할 수 있는 API 입니다.")
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping
    @Operation(summary = "고객센터 신고 조회 API", description = "ID 와 일치하는 Store 를 조회합니다. ( 고객 / 관리자 )")
    public CommonResponse<Page<InquiryResponse>> getAll(
            @Parameter(name = "searchType",description = "검색하고자 하는 필드")
            @RequestParam(required = false,name = "searchType") InquirySearchType searchType,
            @Parameter(name = "searchValue",description = "검색 키워드 - 검색 타입에 맞게 검색")
            @RequestParam(required = false, name = "searchValue") String searchValue,
            @ParameterObject @PageableDefault(
                    size = 10, sort = {"createdAt", "updatedAt"}, direction = Sort.Direction.DESC
            ) Pageable pageable,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return CommonResponse.success(
                inquiryService.getAllInquiry(searchType, searchValue,userDetails, pageable).map(InquiryResponse::from)
        );
    }


    @GetMapping("/{inquiryId}")
    @Operation(summary = "고객센터 신고 조회 API", description = "ID 와 일치하는 Store 를 조회합니다. ( 고객 / 관리자 )")
    public CommonResponse<InquiryResponse> getOne(
            @PathVariable String inquiryId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return CommonResponse.success(InquiryResponse.from(inquiryService.getInquiry(inquiryId, userDetails)));
    }

    @PostMapping
    @Operation(summary = "고객센터 신고 생성 API", description = "고객센터 신고를 접수하는 API 입니다.")
    public CommonResponse<Void> create(
            @RequestBody InquiryRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        inquiryService.createInquiry(request.toDto(),userDetails);
        return CommonResponse.success("신고 생성 완료") ;
    }

    //신고 사항 수정 -> 제목, 내용
    @PutMapping("/{inquiryId}")
    @Operation(summary = "고객센터 신고 내용 수정 API", description = "고객센터 신고 내용을 수정하는 API 입니다.")
    public CommonResponse<InquiryResponse> update(
            @PathVariable String inquiryId,
            @RequestBody InquiryRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return CommonResponse.success(
                InquiryResponse.from(inquiryService.updateInquiry(inquiryId, request.toDto(), userDetails))
        );
    }


    // 신고자만 삭제 가능
    @DeleteMapping("/{inquiryId}")
    @Operation(summary = "고객센터 신고 삭제 API", description = "고객센터 신고를 삭제하는 API 입니다.")
    public CommonResponse<Void> delete(
            @PathVariable String inquiryId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        inquiryService.deleteInquiry(inquiryId, userDetails);
        return CommonResponse.success("신고 삭제 완료");
    }

    @PostMapping("/{inquiryId}")
    @Operation(summary = "고객센터 신고 답변 API", description = "고객센터 신고에 답변을 작성하는 API 입니다. (관리자/매니저 전용)")
    public CommonResponse<Void> reply(
            @PathVariable String inquiryId,
            @RequestBody InquirySetReplyRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        inquiryService.replyToInquiry(inquiryId, request, userDetails);
        return CommonResponse.success("신고 답변 완료") ;
    }
}
