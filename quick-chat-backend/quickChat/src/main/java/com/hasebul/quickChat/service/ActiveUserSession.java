package com.hasebul.quickChat.service;

import com.hasebul.quickChat.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ActiveUserSession {
    private final Set<User> userSet;

    public ActiveUserSession() {
        this.userSet = new HashSet<>();
    }

    public void addUser(User user) {
        userSet.add(user);
    }

    public void removeUser(User user) {
        userSet.remove(user);
    }

    public List<User> getActiveUsers() {
        return new ArrayList<>(userSet);
    }
}
