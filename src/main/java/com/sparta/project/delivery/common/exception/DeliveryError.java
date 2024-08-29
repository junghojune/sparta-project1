package com.sparta.project.delivery.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryError {

    // Auth (인증 관련 에러)
    AUTH_INVALID_CREDENTIALS(401, "AUTH_001", "유효하지 않은 자격 증명입니다."),
    AUTH_UNAUTHORIZED(403, "AUTH_002", "인증되지 않은 사용자입니다."),
    AUTH_TOKEN_EXPIRED(401, "AUTH_003", "토큰이 만료되었습니다."),

    // Category (카테고리 관련 에러)
    CATEGORY_NOT_FOUND(404, "CATEGORY_001", "카테고리를 찾을 수 없습니다."),
    CATEGORY_ALREADY_EXISTS(409, "CATEGORY_002", "이미 존재하는 카테고리입니다."),

    // Inquiry (고객 센터 문의 관련 에러)
    INQUIRY_NOT_FOUND(404, "INQUIRY_001", "문의 내용을 찾을 수 없습니다."),
    INQUIRY_UPDATE_FAILED(400, "INQUIRY_002", "문의 내용을 업데이트할 수 없습니다."),

    // Menu (메뉴 관련 에러)
    MENU_NOT_FOUND(404, "MENU_001", "메뉴를 찾을 수 없습니다."),
    MENU_CREATION_FAILED(400, "MENU_002", "메뉴 생성에 실패하였습니다."),
    MENU_UPDATE_FAILED(400, "MENU_003", "메뉴 업데이트에 실패하였습니다."),
    MENU_DELETE_FAILED(400, "MENU_004", "메뉴 삭제에 실패하였습니다."),

    // Notice (공지사항 관련 에러)
    NOTICE_NOT_FOUND(404, "NOTICE_001", "공지사항을 찾을 수 없습니다."),
    NOTICE_CREATION_FAILED(400, "NOTICE_002", "공지사항 생성에 실패하였습니다."),
    NOTICE_UPDATE_FAILED(400, "NOTICE_003", "공지사항 업데이트에 실패하였습니다."),
    NOTICE_DELETE_FAILED(400, "NOTICE_004", "공지사항 삭제에 실패하였습니다."),

    // Order (주문 관련 에러)
    ORDER_NOT_FOUND(404, "ORDER_001", "주문을 찾을 수 없습니다."),
    ORDER_CREATION_FAILED(400, "ORDER_002", "주문 생성에 실패하였습니다."),
    ORDER_UPDATE_FAILED(400, "ORDER_003", "주문 업데이트에 실패하였습니다."),
    ORDER_CANCEL_FAILED(400, "ORDER_004", "주문 취소에 실패하였습니다."),

    // Region (지역 관련 에러)
    REGION_NOT_FOUND(404, "REGION_001", "지역을 찾을 수 없습니다."),
    REGION_CREATION_FAILED(400, "REGION_002", "지역 생성에 실패하였습니다."),
    REGION_UPDATE_FAILED(400, "REGION_003", "지역 업데이트에 실패하였습니다."),

    // Review (리뷰 관련 에러)
    REVIEW_NOT_FOUND(404, "REVIEW_001", "리뷰를 찾을 수 없습니다."),
    REVIEW_CREATION_FAILED(400, "REVIEW_002", "리뷰 생성에 실패하였습니다."),
    REVIEW_UPDATE_FAILED(400, "REVIEW_003", "리뷰 업데이트에 실패하였습니다."),
    REVIEW_DELETE_FAILED(400, "REVIEW_004", "리뷰 삭제에 실패하였습니다."),

    // Store (가게 관련 에러)
    STORE_NOT_FOUND(404, "STORE_001", "가게를 찾을 수 없습니다."),
    STORE_CREATION_FAILED(400, "STORE_002", "가게 생성에 실패하였습니다."),
    STORE_UPDATE_FAILED(400, "STORE_003", "가게 업데이트에 실패하였습니다."),
    STORE_DELETE_FAILED(400, "STORE_004", "가게 삭제에 실패하였습니다."),

    // User (사용자 관련 에러)
    USER_NOT_FOUND(404, "USER_001", "사용자를 찾을 수 없습니다."),
    USER_CREATION_FAILED(400, "USER_002", "사용자 생성에 실패하였습니다."),
    USER_UPDATE_FAILED(400, "USER_003", "사용자 정보 업데이트에 실패하였습니다."),
    USER_DELETE_FAILED(400, "USER_004", "사용자 삭제에 실패하였습니다."),

    // AI 도메인 관련 에러
    AI_QUESTION_INVALID(400, "AI_001", "질문은 1자 이상 50자 이하로 작성해야 합니다."),
    AI_COMPLETION_FAILED(500, "AI_002", "AI 응답 생성에 실패하였습니다."),
    AI_RESPONSE_NOT_FOUND(404, "AI_003", "AI 응답을 찾을 수 없습니다."),
    AI_SERVICE_UNAVAILABLE(503, "AI_004", "AI 서비스가 현재 사용 불가능합니다."),
    AI_MODEL_NOT_CONFIGURED(500, "AI_005", "AI 모델이 설정되지 않았습니다."),
    AI_REQUEST_TIMEOUT(504, "AI_006", "AI 요청이 시간 초과되었습니다.");

    private final int statusCode; // HTTP 상태 코드
    private final String errorCode; // 내부 시스템의 에러 코드
    private final String message; // 에러 메시지
}
