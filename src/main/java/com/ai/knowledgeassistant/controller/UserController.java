package com.ai.knowledgeassistant.controller;

import com.ai.knowledgeassistant.response.LoginRequest;
import com.ai.knowledgeassistant.response.UserRequestDTO;
import com.ai.knowledgeassistant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDTO userRequest) {
        return ResponseEntity.ok(service.register(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(service.login(loginRequest));
    }
}
