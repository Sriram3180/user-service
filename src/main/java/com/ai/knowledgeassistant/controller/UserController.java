package com.ai.knowledgeassistant.controller;

import com.ai.knowledgeassistant.User;
import com.ai.knowledgeassistant.response.UserResponseDTO;
import com.ai.knowledgeassistant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
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
    public ResponseEntity<String> registerUser(@RequestBody UserResponseDTO userRequest) {
        return ResponseEntity.ok(service.register(userRequest));
    }
}
