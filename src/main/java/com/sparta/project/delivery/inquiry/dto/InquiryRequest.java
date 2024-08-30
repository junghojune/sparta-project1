package com.sparta.project.delivery.inquiry.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InquiryRequest(
        @NotBlank(message = "제목은 비어 있을 수 없습니다.")
        @Size(max = 100, message = "제목은 최대 100자까지 입력할 수 있습니다.")
        String title,

        @NotBlank(message = "내용은 비어 있을 수 없습니다.")
        @Size(max = 5000, message = "내용은 최대 5000자까지 입력할 수 있습니다.")
        String content
) {

    public InquiryDto toDto(){
        return InquiryDto.builder()
                .title(title)
                .content(content)
                .build();
    }
}
