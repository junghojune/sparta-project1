package com.sparta.project.delivery.notice.service;


import com.sparta.project.delivery.notice.constant.NoticeSearchType;
import com.sparta.project.delivery.notice.dto.NoticeDto;
import com.sparta.project.delivery.notice.dto.NoticeRequest;
import com.sparta.project.delivery.notice.entity.Notice;
import com.sparta.project.delivery.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;


    public String createNotice(NoticeDto dto) {
        noticeRepository.save(dto.toEntity());
        return "created";
    }

    @Transactional(readOnly = true)
    public Page<NoticeDto> getAllNotices(NoticeSearchType searchType, String searchValue, Pageable pageable) {
        if (searchValue == null || searchValue.isBlank()) {
            return noticeRepository.findAll(pageable).map(NoticeDto::from);
        }

        return switch (searchType){
            case TITLE -> noticeRepository.findByTitleContainingIgnoreCase(searchValue, pageable).map(NoticeDto::from);
            case CONTENT -> noticeRepository.findByContentContaining(searchValue, pageable).map(NoticeDto::from);
        };
    }

    @Transactional(readOnly = true)
    public NoticeDto getNotice(String noticeId) {
        return noticeRepository.findById(noticeId).map(NoticeDto::from).orElseThrow();
    }


    @Transactional
    public NoticeDto updateNotice(String noticeId, NoticeRequest request) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow();

        notice.setTitle(request.title());
        notice.setContent(request.content());

        return NoticeDto.from(noticeRepository.save(notice));
    }

    public String deleteNotice(String noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow();
        notice.setIsDeleted(true);
        notice.setIsPublic(true);
        notice.setDeletedAt(LocalDateTime.now());
        notice.setDeletedBy("MASTER");
        return "deleted";
    }
}
