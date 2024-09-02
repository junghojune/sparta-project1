package com.sparta.project.delivery.inquiry.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InquirySetReplyRequest(
        @NotBlank(message = "답변은 비어 있을 수 없습니다.")
        @Size(max = 5000, message = "내용은 최대 5000자까지 입력할 수 있습니다.")
        String response
) {
}
