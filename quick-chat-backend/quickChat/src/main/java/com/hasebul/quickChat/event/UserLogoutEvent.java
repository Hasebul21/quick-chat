package com.hasebul.quickChat.event;

import com.hasebul.quickChat.dto.LoginDto;
import com.hasebul.quickChat.model.Users;

public class UserLogoutEvent {
    private final Users user;

    public UserLogoutEvent(Users user) {
        this.user = user;
    }

    public Users getUser() {
        return user;
    }
}