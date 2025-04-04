package com.hasebul.quickChat.service;

import com.hasebul.quickChat.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserSession {
    private final Set<UserDto> userIdCache = new HashSet<>();


    public Boolean addUserToSession(UserDto userDto) throws Exception {
        System.out.println(this.userIdCache);
        if(isIdExist(userDto))
            throw new Exception(userDto.getId() + " exist in the session");
        userIdCache.add(userDto);
        return true;
    }

    public Boolean removeUserFromSession(UserDto userDto) throws Exception{
        if(!isIdExist(userDto))
            throw new Exception(userDto.getId() + " does not exist in the session");
        userIdCache.remove(userDto);
        return true;
    }

    public boolean isIdExist(UserDto user){
        return userIdCache.contains(user);
    }

    public UserDto findUserById(String userId){
        return userIdCache.stream()
                .filter(user -> user.getId().equals(userId))
                .findAny()
                .orElse(null);
    }

    public List<UserDto> getAllUser(){
        return userIdCache.stream().toList();
    }
}
