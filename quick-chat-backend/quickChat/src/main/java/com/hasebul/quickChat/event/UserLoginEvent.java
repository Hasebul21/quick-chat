package com.hasebul.quickChat.event;

import com.hasebul.quickChat.dto.LoginDto;

public class UserLoginEvent {
    private final LoginDto loginDto;

    public UserLoginEvent(LoginDto loginDto) {
        this.loginDto = loginDto;
    }

    public LoginDto getLoginDto() {
        return loginDto;
    }
}
