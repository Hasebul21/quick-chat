package com.hasebul.quickChat.event;

import com.hasebul.quickChat.dto.LoginDto;

public class UserLogoutEvent {
    private final LoginDto loginDto;

    public UserLogoutEvent(LoginDto loginDto) {
        this.loginDto = loginDto;
    }

    public LoginDto getLoginDto() {
        return loginDto;
    }
}