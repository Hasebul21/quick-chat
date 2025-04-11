package com.hasebul.quickChat.service;

import com.hasebul.quickChat.model.Users;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ActiveUserSession {
    private final Set<Users> usersSet;

    public ActiveUserSession() {
        this.usersSet = new HashSet<>();
    }

    public void addUser(Users user) {
        usersSet.add(user);
    }

    public void removeUser(Users user) {
        usersSet.remove(user);
    }

    public List<Users> getActiveUsers() {
        return new ArrayList<>(usersSet);
    }
}
