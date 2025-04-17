package com.hasebul.quickChat.repository;

import com.hasebul.quickChat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepo extends JpaRepository<User, Long> {
    Optional<User> findByUserEmailAndPassword(String userEmail, String password);
    Optional<User> findByUserEmail(String userEmail);
}
