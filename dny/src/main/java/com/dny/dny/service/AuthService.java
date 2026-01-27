package com.dny.dny.service;

import com.dny.dny.dto.LoginRequest;
import com.dny.dny.dto.SignupRequest;
import com.dny.dny.entity.User;
import com.dny.dny.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // 회원가입
    public void signup(SignupRequest request) {

        if (userRepository.findByLoginId(request.getLoginId()).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디");
        }

        User user = new User();
        user.setLoginId(request.getLoginId());
        //비밀번호 해시
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    // 로그인
    public void login(LoginRequest request, HttpSession session) {

        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new RuntimeException("아이디 없음"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호 틀림");
        }

        // ✔ 로그인 성공 시 세션에 userId 저장
        session.setAttribute("userId", user.getUserId());
    }

    // 로그아웃
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
