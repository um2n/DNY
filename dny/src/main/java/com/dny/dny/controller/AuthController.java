package com.dny.dny.controller;

import com.dny.dny.dto.LoginRequest;
import com.dny.dny.dto.SignupRequest;
import com.dny.dny.entity.User;
import com.dny.dny.repository.UserRepository;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequest request) {

        // 아이디 중복 체크
        if (userRepository.findById(request.getId()).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디");
        }

        User user = new User();
        user.setId(request.getId());
        user.setPassword(request.getPassword()); // 지금은 평문, 다음 단계에서 암호화

        userRepository.save(user);
    }
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request) {

        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("아이디 없음"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("비밀번호 틀림");
        }
    }


}