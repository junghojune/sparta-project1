package com.sparta.project.delivery.store.constant;

import lombok.Getter;

@Getter
public enum StoreSearchType {
    NAME("이름"),
    Description("설명");

    private final String description;

    StoreSearchType(String description) {
        this.description = description;
    }
}
