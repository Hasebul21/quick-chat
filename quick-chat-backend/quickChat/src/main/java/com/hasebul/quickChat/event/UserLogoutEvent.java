package com.hasebul.quickChat.event;

import com.hasebul.quickChat.model.User;

public class UserLogoutEvent {
    private final User user;

    public UserLogoutEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}