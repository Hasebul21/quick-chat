package com.hasebul.quickChat.service;

import com.hasebul.quickChat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisService {

    @Value("${active.user.key}")
    private String activeUserKey;

    @Autowired
    private RedisTemplate redisTemplate;

    public void addActiveUser(User user) {
        redisTemplate.opsForSet().add(activeUserKey, user);
    }

    public void removeActiveUser(User user) {
        redisTemplate.opsForSet().remove(activeUserKey, user);
    }

    public List<User> getActiveUsers() {
        return redisTemplate.opsForSet().members(activeUserKey).stream().toList();
    }
}
