package com.sparta.project.delivery.user.controller;

import com.sparta.project.delivery.auth.JwtUtil;
import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.common.response.CommonResponse;
import com.sparta.project.delivery.user.dto.UserDto;
import com.sparta.project.delivery.user.dto.request.DeleteUser;
import com.sparta.project.delivery.user.dto.request.LoginRequest;
import com.sparta.project.delivery.user.dto.request.SignupRequest;
import com.sparta.project.delivery.user.dto.request.UpdateUser;
import com.sparta.project.delivery.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
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
    @Operation(summary = "회원 가입 API", description = "User을 생성, 등록합니다.")
    public CommonResponse<Void> signup(@RequestBody SignupRequest requestDto) {
        return userService.signup(requestDto.toDto());
    }

    // 로그인
    @PostMapping("/oauth/login")
    @Operation(summary = "회원 로그인 API", description = "User를 이용해 로그인, jwt 토큰을 헤더로 받습니다.")
    public CommonResponse<Void> login(@RequestBody LoginRequest request, HttpServletResponse res){
            return userService.login(request.toDto(),res);
    }

    // 로그아웃
    @PostMapping("/users/logout")
    @Operation(summary = "로그아웃 API", description = "헤더에서 토큰을 삭제합니다.")
    public CommonResponse<Void> logout(@AuthenticationPrincipal UserDetailsImpl userDetails
     ,HttpServletRequest request, HttpServletResponse response){

        return userService.logout(userDetails.getUser(),request,response);
    }

    // 사용자 정보 조회
    @GetMapping("/users")
    @Operation(summary = "사용자 조회 API", description = "User을 조회합니다.")
    public CommonResponse<UserDto> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getUser(userDetails.getUser());

    }

    // 사용자 정보 수정
    @PutMapping("/users")
    @Operation(summary = "사용자 업데이트 API", description = "User의 정보를 수정합니다.")
    public CommonResponse<UserDto> update(@RequestBody UpdateUser request, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse res){
        return userService.update(request.toDto(),userDetails.getUser(), res);
    }

    // 회원 탈퇴
    @DeleteMapping("/users")
    @Operation(summary = "회원 탈퇴", description = "User을 삭제 (isDeleted = true) 합니다.")
    public CommonResponse<Void> delete(@RequestBody DeleteUser request,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.delete(request.toDto(),userDetails.getUser());
    }
}
