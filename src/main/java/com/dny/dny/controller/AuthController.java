package com.dny.dny.controller;

import com.dny.dny.dto.ApiResponse;
import com.dny.dny.dto.LoginRequest;
import com.dny.dny.dto.SignupRequest;
import com.dny.dny.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ApiResponse<Void> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ApiResponse.success("회원가입이 완료되었습니다.", null);
    }

    // 로그인
    @PostMapping("/login")
    public ApiResponse<Void> login(@Valid @RequestBody LoginRequest request,
                      HttpSession session) {
        authService.login(request, session);
        return ApiResponse.success("로그인에 성공하였습니다.", null);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpSession session) {
        session.invalidate();
        return ApiResponse.success("로그아웃 되었습니다.", null);
    }
}
