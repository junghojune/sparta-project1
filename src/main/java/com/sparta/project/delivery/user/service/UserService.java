package com.sparta.project.delivery.user.service;

import com.sparta.project.delivery.address.dto.AddressDto;
import com.sparta.project.delivery.auth.JwtUtil;
import com.sparta.project.delivery.common.exception.CustomException;
import com.sparta.project.delivery.common.response.CommonResponse;
import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.dto.UserDto;
import com.sparta.project.delivery.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sparta.project.delivery.common.exception.DeliveryError.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN 추후 백오피스 구현 시 (한다면..?) 상세 수정 및 구현
//    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public CommonResponse<Void> signup(UserDto requestDto) {

        // email, username 중복 확인
        checkDuplicateUser(requestDto);

        // Admin Token 사용 시 검증 과정 여기서 구현

        // 사용자 등록
        User user = User.builder()
                .username(requestDto.username())
                .email(requestDto.email())
                .password(passwordEncoder.encode(requestDto.password()))
                .role(requestDto.role())
                .build();

        userRepository.save(user);

        return CommonResponse.success("회원가입이 완료되었습니다.");
    }

    public CommonResponse<Void> login(UserDto requestDto, HttpServletResponse res) {
        // email, password 매치 확인
        String email = requestDto.email();
        String password = requestDto.password();

        // 사용자 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(USER_NOT_FOUND)
        );

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(USER_WRONG_PASSWORD);
        }

        // 삭제된 사용자인지 확인
        if(user.getIsDeleted()){
            throw new CustomException(USER_DELETED);
        }

        // JWT 생성
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());

        // JWT를 HTTP 응답 헤더에 추가
        res.setHeader("Authorization", token);

        // 로그인 완료 메시지 반환
        return CommonResponse.success("로그인 완료. 사용자 " + email);
    }

    @Transactional
    public CommonResponse<UserDto> update(UserDto requestDto, User user, HttpServletResponse res) {

        // email, username 중복 확인
        checkDuplicateUser(requestDto);

        user.setEmail(requestDto.email());
        user.setUsername(requestDto.username());


        if (requestDto.password() != null && !requestDto.password().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(requestDto.password());
            user.setPassword(encodedPassword);
        }

        // 업데이트된 사용자 정보 저장
        User updatedUser = userRepository.save(user);

        // 업데이트된 정보 기반 토큰 생성
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());

        // JWT를 HTTP 응답 헤더에 추가
        res.setHeader("Authorization", token);

        // UserDto로 변환하여 반환
        return CommonResponse.success(UserDto.from(updatedUser));
    }

    @Transactional
    public CommonResponse<Void> delete(UserDto requestDto, User user) {

        // 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new CustomException(USER_WRONG_PASSWORD);
        }

        if(user.getIsDeleted()){
            throw new CustomException(USER_DELETED);
        }

        user.setIsDeleted(true);

        // 업데이트된 사용자 정보 저장
        User updatedUser = userRepository.save(user);

        return CommonResponse.success("회원 탈퇴 완료");
    }

    public CommonResponse<Void> logout(User user, HttpServletRequest request, HttpServletResponse response) {
        // 헤더에서 토큰 추출
        String token = extractTokenFromHeader(request);

        // 토큰이 존재하지 않는 경우
        if (token == null || token.isEmpty()) {
            throw new CustomException(AUTH_TOKEN_EXPIRED);
        }

//        // 쿠키에서 토큰 제거
//        jwtUtil.removeJwtFromCookie(response);

        return CommonResponse.success("로그아웃 완료");
    }

    public CommonResponse<UserDto> getUser(User user) {
        return null;
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // "Bearer " 제거 후 토큰 반환
        }
        return null;
    }

    // 이메일, username 중복 체크
    private void checkDuplicateUser (UserDto dto) {
        // email 중복확인
        Optional<User> checkEmail = userRepository.findByEmail(dto.email());
        if (checkEmail.isPresent()) {
            throw new CustomException(EMAIL_ALREADY_EXISTS);
        }

        // 사용자 이름 중복 확인
        if (dto.username() != null && !dto.username().isEmpty()) {
            Optional<User> existingUsernameUser = userRepository.findByUsername(dto.username());
            if (existingUsernameUser.isPresent()) {
                throw new CustomException(USERNAME_ALREADY_EXISTS);
            }
        }
    }
}
