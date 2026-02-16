package com.dny.dny.service;

import com.dny.dny.dto.LoginRequest;
import com.dny.dny.dto.SignupRequest;
import com.dny.dny.entity.User;
import com.dny.dny.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    /**
     * 신규 회원 가입 (아이디 중복 체크 포함)
     */
    @Transactional
    public User signup(SignupRequest request) {
        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        User user = new User();
        user.setLoginId(request.getLoginId());
        user.setPassword(request.getPassword()); // 비밀번호 암호화 권장

        return userRepository.save(user);
    }

    /**
     * 로그인 검증 (아이디 및 비밀번호 확인)
     */
    public User login(LoginRequest request) {
        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}
