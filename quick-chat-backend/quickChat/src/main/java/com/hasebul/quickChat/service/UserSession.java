package com.hasebul.quickChat.service;

import com.hasebul.quickChat.dto.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserSession {
    private final Set<User> userIdCache = new HashSet<>();


    public Boolean addUserToSession(User user) throws Exception {
        System.out.println(this.userIdCache);
        if(isIdExist(user))
            throw new Exception(user.getUserId() + " exist in the session");
        userIdCache.add(user);
        return true;
    }

    public Boolean removeUserFromSession(User user) throws Exception{
        if(!isIdExist(user))
            throw new Exception(user.getUserId() + " does not exist in the session");
        userIdCache.remove(user);
        return true;
    }

    public boolean isIdExist(User user){
        return userIdCache.contains(user);
    }

    public User findUserById(String userId){
        return userIdCache.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny()
                .orElse(null);
    }

    public List<User> getAllUser(){
        return userIdCache.stream().toList();
    }
}
