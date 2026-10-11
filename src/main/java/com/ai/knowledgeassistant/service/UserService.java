package com.ai.knowledgeassistant.service;

import com.ai.knowledgeassistant.User;
import com.ai.knowledgeassistant.repository.UserRepository;
import com.ai.knowledgeassistant.response.UserResponseDTO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public String register(UserResponseDTO userRequest){
        if(userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Already Exists !");

        }
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .role("USER").build();
        userRepository.save(user);
        return "User Created Successfully";

    }
}
