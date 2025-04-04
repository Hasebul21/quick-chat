package com.hasebul.quickChat.controller;

import com.hasebul.quickChat.dto.UserDto;
import com.hasebul.quickChat.model.Users;
import com.hasebul.quickChat.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

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

    @GetMapping("auth/users/{useremail}/{password}")
    public Users getUserByUserNameAndPassword(@PathVariable("useremail") String useremail,
                                              @PathVariable("password") String password) throws Exception {
        return authService.getUserByUserNameAndPassword(useremail, password);
    }
}
