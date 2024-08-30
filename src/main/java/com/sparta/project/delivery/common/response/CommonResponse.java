package com.sparta.project.delivery.common.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonResponse<T> {

    private int status;
    private String message;
    private T data;

    public CommonResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }


    // 엔티티 조회 / 수정 성공 반환
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(200, "Success", data);
    }


    // 엔티티 생성 / 삭제 성공 반환
    public static <T> CommonResponse<T> success(String message) {
        return new CommonResponse<>(200, message);
    }

}
