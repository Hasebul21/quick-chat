package com.hasebul.quickChat.repository;

import com.hasebul.quickChat.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByUserEmailAndPassword(String userEmail, String password);
}
