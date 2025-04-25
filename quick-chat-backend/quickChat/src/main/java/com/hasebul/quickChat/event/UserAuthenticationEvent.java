package com.hasebul.quickChat.event;

import com.hasebul.quickChat.dto.LoginDto;
import com.hasebul.quickChat.service.ActiveUserSession;
import com.hasebul.quickChat.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserAuthenticationEvent {
    @Autowired
    private RedisService redisService;

    @EventListener
    @Async
    public void UserLoginEvent(UserLoginEvent userLoginEvent) {
        System.out.println("User Login Event Triggered");
        redisService.addActiveUser(userLoginEvent.getUser());
    }

    @EventListener
    @Async
    public void UserLogoutEvent(UserLogoutEvent userLogoutEvent) {
        System.out.println("User Logout Event Triggered");
        redisService.removeActiveUser(userLogoutEvent.getUser());
    }


    @EventListener
    @Async
    public void updateUserEvent(UserUpdateEvent userUpdateEvent){
        System.out.println("User Update Event Triggered");
        redisService.addActiveUser(userUpdateEvent.getUser());
    }
}
