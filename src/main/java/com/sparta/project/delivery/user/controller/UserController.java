package com.sparta.project.delivery.user.controller;

import com.sparta.project.delivery.auth.JwtUtil;
import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.user.dto.UserDto;
import com.sparta.project.delivery.user.dto.request.DeleteUser;
import com.sparta.project.delivery.user.dto.request.LoginRequest;
import com.sparta.project.delivery.user.dto.request.SignupRequest;
import com.sparta.project.delivery.user.dto.request.UpdateUser;
import com.sparta.project.delivery.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    // 회원 가입
    @PostMapping("/oauth/signup")
    public String signup(@RequestBody SignupRequest requestDto) {
        return userService.signup(requestDto.toDto());
    }

    // 로그인
    @PostMapping("/oauth/login")
    public String login(@RequestBody LoginRequest request, HttpServletResponse res){
        try{
            return userService.login(request.toDto(),res);
        }catch (Exception e){
            new IllegalArgumentException("로그인에 실패했습니다.");
        }
        return "로그인에 실패했습니다";
    }

    // 로그아웃
    @PostMapping("/users/logout")
    public String logout(@AuthenticationPrincipal UserDetailsImpl userDetails){

        return userService.logout(userDetails.getUser());
    }

    // 사용자 정보 조회
    @GetMapping("/users")
    public String getUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getUser(userDetails.getUser());

    }

    // 사용자 정보 수정
    @PutMapping("/users")
    public UserDto update(@RequestBody UpdateUser request, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.update(request.toDto(),userDetails.getUser());
    }

    // 회원 탈퇴
    @DeleteMapping("/users")
    public String delete(@RequestBody DeleteUser request,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.delete(request.toDto(),userDetails.getUser());
    }
}
