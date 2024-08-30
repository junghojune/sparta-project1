package com.sparta.project.delivery.inquiry.service;


import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.exception.CustomException;
import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.inquiry.constant.InquirySearchType;
import com.sparta.project.delivery.inquiry.constant.InquiryStatus;
import com.sparta.project.delivery.inquiry.dto.InquiryDto;
import com.sparta.project.delivery.inquiry.dto.InquirySetReplyRequest;
import com.sparta.project.delivery.inquiry.entity.Inquiry;
import com.sparta.project.delivery.inquiry.repository.InquiryRepository;
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.sparta.project.delivery.common.exception.DeliveryError.*;

@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public Page<InquiryDto> getAllInquiry(InquirySearchType searchType, String searchValue, UserDetailsImpl userDetails, Pageable pageable) {
        User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return inquiryRepository.searchInquiry(searchType, searchValue, user, pageable).map(InquiryDto::from);
    }

    @Transactional(readOnly = true)
    public InquiryDto getInquiry(
            String inquiryId,
            UserDetailsImpl userDetails
    ) {
        User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return InquiryDto.from(inquiryRepository.findInquiryByIdByAuth(inquiryId, user));
    }


    public void createInquiry(InquiryDto dto, UserDetailsImpl userDetails){
        //유저 조회
        User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Inquiry inquiry = Inquiry.builder()
                .user(user)
                .title(dto.title())
                .content(dto.content())
                .status(InquiryStatus.RECEIVED)
                .build();
        inquiryRepository.save(inquiry);
    }


    @Transactional
    public InquiryDto updateInquiry(String inquiryId, InquiryDto dto, UserDetailsImpl userDetails) {
        Inquiry inquiry = getById(inquiryId);
        User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        if (!inquiry.getUser().getEmail().equals(user.getEmail())) {
            throw new CustomException(INQUIRY_UPDATE_FAILED);
        }
        //내용 수정
        inquiry.setTitle(dto.title());
        inquiry.setContent(dto.content());
        return InquiryDto.from(inquiryRepository.save(inquiry));
    }


    @Transactional
    public void deleteInquiry(String inquiryId, UserDetailsImpl userDetails) {
        Inquiry inquiry = getById(inquiryId);
        User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        if (!inquiry.getUser().getEmail().equals(user.getEmail())) {
            throw new CustomException(INQUIRY_UPDATE_FAILED);
        }
        inquiry.deleteInquiry(LocalDateTime.now(), user.getEmail());
    }


    //관리자 OR 매니저만 신고 내용 답변 달기
    @Transactional
    public void replyToInquiry(String inquiryId, InquirySetReplyRequest request, UserDetailsImpl userDetails) {
        Inquiry inquiry = getById(inquiryId);
        User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        if (!user.getRole().equals(UserRoleEnum.MASTER) && !user.getRole().equals(UserRoleEnum.MANAGER)){
            throw new CustomException(INQUIRY_UPDATE_FAILED);
        }
        //답변 달면 완료로 상태 수정
        inquiry.setResponse(request.response());
        inquiry.setStatus(InquiryStatus.COMPLETED);
    }

    private Inquiry getById(String inquiryId){
        return inquiryRepository.findById(inquiryId).orElseThrow(()-> new CustomException(INQUIRY_NOT_FOUND));
    }
}
