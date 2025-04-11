package com.hasebul.quickChat.event;

import com.hasebul.quickChat.dto.LoginDto;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationEvent {

    @EventListener
    @Async
    public void UserLoginEvent(UserLoginEvent userLoginEvent) {
        System.out.println("User Login Event Triggered");
        System.out.println("User Email: " + userLoginEvent.getLoginDto().getUserEmail());
    }

    @EventListener
    @Async
    public void UserLogoutEvent(UserLogoutEvent userLogoutEvent) {
        System.out.println("User Logout Event Triggered");
        System.out.println("User Email: " + userLogoutEvent.getLoginDto().getUserEmail());
    }
}
