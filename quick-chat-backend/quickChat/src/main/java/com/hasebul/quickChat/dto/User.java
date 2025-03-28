package com.hasebul.quickChat.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
public class User {
    private String userId;
    private String userEmail;
    private String userName;
    private LocalDateTime createdOn;

    public User(){
        this.userId = UUID.randomUUID().toString();
        this.createdOn = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(userEmail, user.userEmail) && Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userEmail, userName);
    }
}
