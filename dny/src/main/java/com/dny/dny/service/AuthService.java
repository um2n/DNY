package com.dny.dny.service;

import com.dny.dny.dto.LoginRequest;
import com.dny.dny.dto.SignupRequest;
import com.dny.dny.entity.User;
import com.dny.dny.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public void signup(SignupRequest request) {

        // 아이디 중복 체크
        if (userRepository.findById(request.getId()).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디");
        }

        User user = new User();
        user.setId(request.getId());
        user.setPassword(request.getPassword()); // 추후 암호화

        userRepository.save(user);
    }

    // 로그인
    public void login(LoginRequest request) {

        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("아이디 없음"));


        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("비밀번호 틀림");
        }
    }
}
