package com.sparta.project.delivery.inquiry.service;


import com.sparta.project.delivery.inquiry.constant.InquirySearchType;
import com.sparta.project.delivery.inquiry.constant.InquiryStatus;
import com.sparta.project.delivery.inquiry.dto.InquiryDto;
import com.sparta.project.delivery.inquiry.dto.InquiryRequest;
import com.sparta.project.delivery.inquiry.dto.InquirySetReplyRequest;
import com.sparta.project.delivery.inquiry.entity.Inquiry;
import com.sparta.project.delivery.inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

//TODO : 유저 조건 추가 -> 쿼리 수정
@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;
//    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public Page<InquiryDto> getAllInquiry(InquirySearchType searchType, String searchValue, Pageable pageable) {
        if (searchValue == null || searchValue.isBlank()) {
//            return inquiryRepository.findByUser_Email(pageable).map(InquiryDto::from);
            return inquiryRepository.findAll(pageable).map(InquiryDto::from);
        }

        //TODO : status 조건 추가 생각해보기
        return switch (searchType){
            case TITLE -> inquiryRepository.findByTitleContainingIgnoreCase(searchValue, pageable).map(InquiryDto::from);
            case CONTENT -> inquiryRepository.findByContentContaining(searchValue, pageable).map(InquiryDto::from);
        };
    }

    @Transactional(readOnly = true)
    public InquiryDto getInquiry(
            String inquiryId
//            UserDto userDto or  UserDetailsImpl userDetails
    ) {
        return inquiryRepository.findById(inquiryId).map(InquiryDto::from).orElseThrow();
    }


    public String createInquiry(InquiryDto dto){
        //유저 조회

        //status 수정
        Inquiry inquiry = Inquiry.builder()
//                .user(user)
                .title(dto.title())
                .content(dto.content())
                .status(dto.status())
                .build();
        inquiryRepository.save(inquiry);
        return "created";
    }


    public InquiryDto updateInquiry(String inquiryId, InquiryDto dto) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow();

        inquiry.setTitle(dto.title());
        inquiry.setContent(dto.content());

        return InquiryDto.from(inquiryRepository.save(inquiry));
    }


    @Transactional
    public String deleteInquiry(String inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow();
        inquiry.setIsPublic(false);
        inquiry.setIsDeleted(true);
        inquiry.setDeletedAt(LocalDateTime.now());
        inquiry.setDeletedBy("CUSTOMER");

        return "deleted";
    }


    //관리자 신고 내용 답변 달기
    @Transactional
    public String replyToInquiry(String inquiryId, InquirySetReplyRequest request) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow();

        //답변 달면 완료로 상태 수정
        inquiry.setResponse(request.response());
        inquiry.setStatus(InquiryStatus.COMPLETED);
        return "replyToInquiry";
    }
}
