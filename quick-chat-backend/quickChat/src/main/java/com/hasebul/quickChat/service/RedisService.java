package com.hasebul.quickChat.service;

import com.hasebul.quickChat.dto.RedisUserDto;
import com.hasebul.quickChat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RedisService {


    @Autowired
    private RedisTemplate redisTemplate;

    public void addActiveUser(User user) {
        if(isMember(user))
            removeActiveUser(user);
        RedisUserDto redisUserDto = new RedisUserDto(
                user.getId(),
                user.getUserEmail(),
                user.getUserName(),
                user.getProfessionalTitle(),
                user.getLocation(),
                user.getBio(),
                user.getPortfolio(),
                user.getSkills(),
                user.getHobbies(),
                user.getInstagram(),
                user.getPublishedPostCount(),
                user.getProfileImage()
        );
        redisTemplate.opsForHash().put("activeUsers", user.getId().toString(), redisUserDto);

    }

    public void removeActiveUser(User user) {
        redisTemplate.opsForHash().delete("activeUsers", user.getId().toString());
    }

    public List<RedisUserDto> getActiveUsers() {
        List<Object> userDtos = redisTemplate.opsForHash().values("activeUsers");
        List<RedisUserDto> activeUsers = new ArrayList<>();
        for (Object userDto : userDtos) {
            activeUsers.add((RedisUserDto) userDto);
        }
        return activeUsers;
    }

    public boolean isMember(User user) {
        return redisTemplate.opsForHash().hasKey("activeUsers", user.getId().toString());
    }
}
