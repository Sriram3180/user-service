package com.ai.knowledgeassistant.service;

import com.ai.knowledgeassistant.User;
import com.ai.knowledgeassistant.repository.UserRepository;
import com.ai.knowledgeassistant.response.LoginRequest;
import com.ai.knowledgeassistant.response.UserRequestDTO;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(UserRequestDTO userRequest){
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Already Exists !");
        }
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role("USER").build();
        userRepository.save(user);
        return "User Created Successfully";
    }
    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->new RuntimeException("User not found"));
        System.out.println("Input Password: " + loginRequest.getPassword());
        System.out.println("Stored Password: " + user.getPassword());

        boolean match = passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword());

        System.out.println("Password Match: " + match);

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        return "Login Successful";
    }
}
