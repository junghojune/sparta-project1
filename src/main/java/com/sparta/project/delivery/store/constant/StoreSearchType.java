package com.sparta.project.delivery.store.constant;

import lombok.Getter;

@Getter
public enum StoreSearchType {
    NAME("이름"),
    DESCRIPTION("설명"),
    MENU("메뉴이름");


    private final String description;

    StoreSearchType(String description) {
        this.description = description;
    }
}
