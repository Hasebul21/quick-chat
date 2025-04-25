package com.hasebul.quickChat.event;

import com.hasebul.quickChat.model.User;

public class UserUpdateEvent {
    private final User user;

    public UserUpdateEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
