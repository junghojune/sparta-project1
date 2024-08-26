package com.sparta.project.delivery.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "p_order")
public class Order {

    @Id
    private String orderId;
    private String request;

    // TODO : 주문 상태(주문 진행, 주문 완료, 주문 취소, 음식 준비 완료, 배달중, 배달 완료, 픽업 완료)
    private String status;

    // TODO : 음식 받는 방식(배달, 픽업)
    private String type;

    // todo : USER, STROE 엔티티 연관관계 작성
    // todo : manyToOne
    private Long user;

    // TODO : manyToOne
    private String store;

    // TODO : manyToOne
    private String address;


    private LocalDateTime createdAt;
    private String createBby;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime deletedAt;
    private String deletedBy;
}
