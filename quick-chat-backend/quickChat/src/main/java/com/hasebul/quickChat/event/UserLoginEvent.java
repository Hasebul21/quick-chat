package com.hasebul.quickChat.event;

import com.hasebul.quickChat.model.User;

public class UserLoginEvent {
    private final User user;

    public UserLoginEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
