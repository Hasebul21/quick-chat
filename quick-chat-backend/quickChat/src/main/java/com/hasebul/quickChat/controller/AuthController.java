package com.hasebul.quickChat.controller;

import com.hasebul.quickChat.dto.LoginDto;
import com.hasebul.quickChat.dto.UserDto;
import com.hasebul.quickChat.event.UserLoginEvent;
import com.hasebul.quickChat.event.UserLogoutEvent;
import com.hasebul.quickChat.model.Users;
import com.hasebul.quickChat.service.ActiveUserSession;
import com.hasebul.quickChat.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ActiveUserSession activeUserSession;


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
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) throws Exception {
        Users user = authService.getUserByUserNameAndPassword(loginDto.getUserEmail(), loginDto.getPassword());
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        applicationEventPublisher.publishEvent(new UserLoginEvent(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("auth/logout")
    public ResponseEntity<?> logout(@RequestBody LoginDto loginDto){
        Users user = authService.findUserByEmail(loginDto.getUserEmail());
        applicationEventPublisher.publishEvent(new UserLogoutEvent(user));
        return ResponseEntity.ok("Logout successful");
    }

    /*
       Delete this method if not needed
     */
    @GetMapping("auth/users/active")
    public List<Users> getActiveUsers() {
        return activeUserSession.getActiveUsers();
    }
}
