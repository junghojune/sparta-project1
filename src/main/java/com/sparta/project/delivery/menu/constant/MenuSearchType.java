package com.sparta.project.delivery.menu.constant;

import lombok.Getter;

@Getter
public enum MenuSearchType {
    NAME("이름"),
    DESCRIPTION("설명");

    private final String description;

    MenuSearchType(String description) {
        this.description = description;
    }
}
