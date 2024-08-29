package com.sparta.project.delivery.inquiry.dto;

import com.sparta.project.delivery.inquiry.constant.InquiryStatus;

public record InquiryRequest(
        String title,
        String content
) {

    public InquiryDto toDto(String userEmail){
        return InquiryDto.builder()
                .userEmail(userEmail)
                .title(title)
                .content(content)
                .status(InquiryStatus.RECEIVED)
                .build();
    }


    public InquiryDto toDto(){
        return InquiryDto.builder()
                .title(title)
                .content(content)
                .build();
    }
}
