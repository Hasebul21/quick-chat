package com.hasebul.quickChat.event;

import com.hasebul.quickChat.dto.LoginDto;
import com.hasebul.quickChat.service.ActiveUserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationEvent {

    @Autowired
    private ActiveUserSession activeUserSession;

    @EventListener
    @Async
    public void UserLoginEvent(UserLoginEvent userLoginEvent) {
        System.out.println("User Login Event Triggered");
        activeUserSession.addUser(userLoginEvent.getUser());
    }

    @EventListener
    @Async
    public void UserLogoutEvent(UserLogoutEvent userLogoutEvent) {
        System.out.println("User Logout Event Triggered");
        activeUserSession.removeUser(userLogoutEvent.getUser());
    }
}
