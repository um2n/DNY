package com.dny.dny.controller;

import com.dny.dny.dto.ApiResponse;
import com.dny.dny.dto.LoginRequest;
import com.dny.dny.dto.SignupRequest;
import com.dny.dny.entity.User;
import com.dny.dny.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입 처리
     */
    @PostMapping("/signup")
    public ApiResponse<User> signup(@RequestBody SignupRequest request) {
        return ApiResponse.success(authService.signup(request));
    }

    /**
     * 로그인 처리 (세션에 사용자 ID 저장)
     */
    @PostMapping("/login")
    public ApiResponse<Void> login(@RequestBody LoginRequest request, HttpSession session) {
        User user = authService.login(request);
        session.setAttribute("userId", user.getUserId());
        return ApiResponse.success("로그인 성공", null);
    }

    /**
     * 로그아웃 처리 (세션 무효화)
     */
    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpSession session) {
        session.invalidate();
        return ApiResponse.success("로그아웃 성공", null);
    }
}
