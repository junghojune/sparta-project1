package com.sparta.project.delivery.inquiry.constant;

import lombok.Getter;

@Getter
public enum InquiryStatus {

    RECEIVED("접수"),     // 접수 상태
    COMPLETED("완료");    // 완료 상태 -> 답변 완료, 처리 중 상태는 생략

    private final String description;

    InquiryStatus(String description) {
        this.description = description;
    }

}
