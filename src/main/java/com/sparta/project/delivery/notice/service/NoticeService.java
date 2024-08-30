package com.sparta.project.delivery.notice.service;


import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.exception.CustomException;
import com.sparta.project.delivery.common.exception.DeliveryError;
import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.notice.constant.NoticeSearchType;
import com.sparta.project.delivery.notice.dto.NoticeDto;
import com.sparta.project.delivery.notice.dto.NoticeRequest;
import com.sparta.project.delivery.notice.entity.Notice;
import com.sparta.project.delivery.notice.repository.NoticeRepository;
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.sparta.project.delivery.common.exception.DeliveryError.*;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    public void createNotice(NoticeDto dto) {
        noticeRepository.save(dto.toEntity());
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

    @Transactional
    public void deleteNotice(String noticeId, UserDetailsImpl userDetails) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new CustomException(NOTICE_NOT_FOUND));
        String userEmail = checkUserRole(userDetails);
        notice.deleteNotice(LocalDateTime.now(), userEmail);
    }

    private String checkUserRole(UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        if (!user.getRole().equals(UserRoleEnum.MASTER) && !user.getRole().equals(UserRoleEnum.MANAGER)){
            throw new CustomException(AUTH_UNAUTHORIZED);
        }
        return user.getEmail();

    }
}
