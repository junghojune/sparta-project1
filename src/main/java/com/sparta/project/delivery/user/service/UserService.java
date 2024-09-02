package com.sparta.project.delivery.user.service;

import com.sparta.project.delivery.auth.JwtUtil;
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

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN 추후 백오피스 구현 시 (한다면..?) 상세 수정 및 구현
//    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public String signup(UserDto requestDto) {
        String username = requestDto.username();
        String password = passwordEncoder.encode(requestDto.password());

        // email 중복확인
        String email = requestDto.email();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = requestDto.role();

        // Admin Token 사용 시 검증 과정 여기서 구현

        // 사용자 등록
        User user = User.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .build();
        userRepository.save(user);

        return "회원 가입이 성공적으로 완료되었습니다.";
    }

    public String login(UserDto requestDto, HttpServletResponse res) {
        // email, password 매치 확인
        String email = requestDto.email();
        String password = requestDto.password();

        // 사용자 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 삭제된 사용자인지 확인
        if(user.getIsDeleted()){
            throw new IllegalArgumentException("탈퇴한 사용자입니다.");
        }

        // JWT 생성
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());

        // JWT를 HTTP 응답 헤더에 추가
        res.setHeader("Authorization", token);

        // 로그인 완료 메시지 반환
        return "로그인 완료. 사용자 " + email;
    }

    @Transactional
    public UserDto update(UserDto requestDto, User user, HttpServletResponse res) {

        // email 중복 확인
        if (requestDto.email() != null && !requestDto.email().isEmpty()) {
            Optional<User> existingUser = userRepository.findByEmail(requestDto.email());
            if (existingUser.isPresent() && !existingUser.get().getUserId().equals(user.getUserId())) {
                throw new IllegalArgumentException("중복된 Email 입니다.");
            }
            user.setEmail(requestDto.email());
        }

        // 사용자 정보 업데이트
        // 사용자 이름 중복 확인
        if (requestDto.username() != null && !requestDto.username().isEmpty()) {
            Optional<User> existingUsernameUser = userRepository.findByUsername(requestDto.username());
            // 사용자 이름이 중복되었고, 중복된 사용자가 현재 사용자가 아닌 경우
            if (existingUsernameUser.isPresent() && !existingUsernameUser.get().getUserId().equals(user.getUserId())) {
                throw new IllegalArgumentException("중복된 사용자 이름입니다.");
            }
            user.setUsername(requestDto.username());
        }

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
        return UserDto.from(updatedUser);
    }

    @Transactional
    public String delete(UserDto requestDto, User user) {

        // 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if(user.getIsDeleted()){
            throw new IllegalArgumentException("이미 탈퇴한 사용자입니다.");
        }

        user.setIsDeleted(true);

        // 업데이트된 사용자 정보 저장
        User updatedUser = userRepository.save(user);

        // UserDto로 변환하여 반환
        return user.getUsername() +  "의 탈퇴가 완료되었습니다.";
    }

    public String logout(User user, HttpServletRequest request, HttpServletResponse response) {
        // 헤더에서 토큰 추출
        String token = extractTokenFromHeader(request);

        // 토큰이 존재하지 않는 경우
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }

//        // 쿠키에서 토큰 제거
//        jwtUtil.removeJwtFromCookie(response);

        return "로그아웃 완료.";
    }

    public String getUser(User user) {
        return null;
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // "Bearer " 제거 후 토큰 반환
        }
        return null;
    }
}
