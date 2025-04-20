package com.hasebul.quickChat.controller;

import com.hasebul.quickChat.dto.UserDto;
import com.hasebul.quickChat.model.User;
import com.hasebul.quickChat.service.RedisService;
import com.hasebul.quickChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;


    @PostMapping("auth/users")
    public ResponseEntity<?> persistUser(@RequestBody  UserDto userDto){
        User user = userService.persistUser(userDto);
        if(user == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User already exists");
        return ResponseEntity.ok(user);
    }

    @GetMapping("auth/users")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("auth/users/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @GetMapping("auth/users/active")
    public List<User> getActiveUsers() {
        return redisService.getActiveUsers();
    }
    
    @PutMapping("auth/users/{id}")
    public ResponseEntity<?> updateInfo(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        User user = userService.updateUserInfo(id, userDto);
        if(user == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        return ResponseEntity.ok(user);
    }
}
