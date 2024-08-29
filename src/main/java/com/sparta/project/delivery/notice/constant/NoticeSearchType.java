package com.sparta.project.delivery.notice.constant;

import lombok.Getter;

@Getter
public enum NoticeSearchType {
    TITLE("제목"),
    CONTENT("본문");

    private final String description;

    NoticeSearchType(String description) {
        this.description = description;
    }
}
