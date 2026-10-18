package com.ai.knowledgeassistant.service;

import com.ai.knowledgeassistant.entity.User;
import com.ai.knowledgeassistant.repository.UserRepository;
import com.ai.knowledgeassistant.response.LoginRequest;
import com.ai.knowledgeassistant.response.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    @Autowired
    private UserRepository userRepository;

    public String register(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Already Exists !");
        }
        User user = User.builder().name(userRequest.getName()).email(userRequest.getEmail()).password(passwordEncoder.encode(userRequest.getPassword())).role("USER").build();
        userRepository.save(user);
        return "User Created Successfully";
    }

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        return jwtService.generateToken(loginRequest.getEmail());
    }
}
