package com.ai.knowledgeassistant.repository;

import com.ai.knowledgeassistant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

    public boolean existsByEmail(String email);
}
