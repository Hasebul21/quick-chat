package com.hasebul.quickChat.controller;

import com.hasebul.quickChat.dto.LoginDto;
import com.hasebul.quickChat.dto.UserDto;
import com.hasebul.quickChat.event.UserLoginEvent;
import com.hasebul.quickChat.event.UserLogoutEvent;
import com.hasebul.quickChat.model.Users;
import com.hasebul.quickChat.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("auth/users")
    public Users persistUser(@RequestBody  UserDto userDto){

        return authService.persistUser(userDto);
    }

    @GetMapping("auth/users")
    public List<Users> getAllUser(){

        return authService.getAllUser();
    }

    @GetMapping("auth/users/{id}")
    public Users getUserById(@PathVariable("id") Long id){

        return authService.getUserById(id);
    }

    @PostMapping("auth/login")
    public Users login(@RequestBody LoginDto loginDto) throws Exception {
        applicationEventPublisher.publishEvent(new UserLoginEvent(loginDto));
        return authService.getUserByUserNameAndPassword(loginDto.getUserEmail(), loginDto.getPassword());
    }

    @PostMapping("auth/logout")
    public void logout(@RequestBody LoginDto loginDto){
        applicationEventPublisher.publishEvent(new UserLogoutEvent(loginDto));
    }
}
