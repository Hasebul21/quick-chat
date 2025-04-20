package com.hasebul.quickChat.controller;

import com.hasebul.quickChat.dto.LoginDto;
import com.hasebul.quickChat.model.User;
import com.hasebul.quickChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("auth/users/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) throws Exception {
        User user = userService.login(loginDto);
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        return ResponseEntity.ok(user);
    }

    @PostMapping("auth/users/logout")
    public ResponseEntity<?> logout(@RequestBody LoginDto loginDto) throws Exception {
        userService.logout(loginDto);
        return ResponseEntity.ok("Logout successful");
    }
}
