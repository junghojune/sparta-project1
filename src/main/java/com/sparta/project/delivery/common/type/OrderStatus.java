package com.sparta.project.delivery.common.type;public enum OrderStatus {    ORDER_IN_PROGRESS("주문 진행"),    ORDER_COMPLETED("주문 완료"),    ORDER_CANCELLED("주문 취소"),    FOOD_READY("음식 준비 완료"),    DELIVERING("배달중"),    DELIVERY_COMPLETED("배달 완료"),    PICKUP_COMPLETED("픽업 완료");    private String name;    OrderStatus(String name){        this.name = name;    }    public String getName(){        return name;    }}