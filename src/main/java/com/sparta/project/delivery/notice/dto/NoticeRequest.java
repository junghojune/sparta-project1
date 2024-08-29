package com.sparta.project.delivery.notice.dto;



public record NoticeRequest(
        String title,
        String content
) {


    public NoticeDto toDto(){
        return NoticeDto.builder().title(title).content(content).build();
    }
}
