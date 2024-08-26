package com.sparta.project.delivery.common;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    CUSTOMER(Authority.CUSTOMER), // 소비자
    OWNER(Authority.OWNER), // 식당 주인
    MANAGER(Authority.MANAGER),
    MASTER(Authority.MASTER); // 관리자

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String OWNER = "ROLE_OWNER";
        public static final String MANAGER = "ROLE_MANAGER";
        public static final String MASTER = "ROLE_MASTER";
    }
}