package com.dny.dny.controller;

import com.dny.dny.dto.LoginRequest;
import com.dny.dny.dto.SignupRequest;
import com.dny.dny.service.AuthService;
import jakarta.servlet.http.HttpSession;
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
    public String signup(@RequestBody SignupRequest request) {
        authService.signup(request);
        return "회원가입 완료";
    }

    // 로그인
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request,
                      HttpSession session) {
        authService.login(request, session);
    }

    // 로그아웃
    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
