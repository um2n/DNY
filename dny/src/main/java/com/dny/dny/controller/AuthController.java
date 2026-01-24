package com.dny.dny.controller;

import com.dny.dny.dto.LoginRequest;
import com.dny.dny.dto.SignupRequest;
import com.dny.dny.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequest request) {
        authService.signup(request);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request) {
        authService.login(request);
    }
}