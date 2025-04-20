package com.hasebul.quickChat.service;

import com.hasebul.quickChat.dto.RedisUserDto;
import com.hasebul.quickChat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RedisService {

    @Value("${active.user.key}")
    private String activeUserKey;

    @Autowired
    private RedisTemplate redisTemplate;

    // Convert User to RedisUserDto and add to Redis set
    public void addActiveUser(User user) {
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
                user.getPublishedPostCount()
        );
        redisTemplate.opsForSet().add(activeUserKey, redisUserDto);
    }

    // Convert User to RedisUserDto and remove from Redis set
    public void removeActiveUser(User user) {
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
                user.getPublishedPostCount()
        );
        redisTemplate.opsForSet().remove(activeUserKey, redisUserDto);
    }

    // Retrieve all active users from Redis and convert to List<RedisUserDto>
    public List<RedisUserDto> getActiveUsers() {
        Set<RedisUserDto> redisUserDtos = redisTemplate.opsForSet().members(activeUserKey);
        return new ArrayList<>(redisUserDtos);
    }
}
