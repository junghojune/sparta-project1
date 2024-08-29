package com.sparta.project.delivery.inquiry.constant;

import lombok.Getter;

@Getter
public enum InquirySearchType {
    TITLE("제목"),
    CONTENT("본문");

    private final String description;

    InquirySearchType(String description) {
        this.description = description;
    }
}
