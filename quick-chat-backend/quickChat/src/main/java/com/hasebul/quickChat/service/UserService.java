package com.hasebul.quickChat.service;

import com.hasebul.quickChat.dto.LoginDto;
import com.hasebul.quickChat.dto.UserDto;
import com.hasebul.quickChat.event.UserLoginEvent;
import com.hasebul.quickChat.event.UserLogoutEvent;
import com.hasebul.quickChat.event.UserUpdateEvent;
import com.hasebul.quickChat.model.User;
import com.hasebul.quickChat.repository.AuthRepo;
import com.hasebul.quickChat.utils.Helper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private AuthRepo authRepo;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RedisService redisService;

    public User persistUser(UserDto userDto) {
        User user = new User(userDto.getUserName(), userDto.getPassword(), userDto.getUserEmail());
        try {
            return authRepo.save(user);
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> getAllUser() {
        List<User> userList = authRepo.findAll();
        return userList;
    }

    public User getUserById(Long id) {
        User user = authRepo.findById(id).get();
        return user;
    }

    public User getUserByUserNameAndPassword(String useremail, String password) throws Exception {
        Optional<User> users = authRepo.findByUserEmailAndPassword(useremail, password);
        return users.orElse(null);
    }

    public User findUserByEmail(String email) {

        return authRepo.findByUserEmail(email).orElse(null);
    }

    public User login(LoginDto loginDto) throws Exception {
        User user = getUserByUserNameAndPassword(loginDto.getUserEmail(), loginDto.getPassword());
        if (user == null)
            return null;
        eventPublisher.publishEvent(new UserLoginEvent(user));
        return user;
    }

    public void logout(LoginDto loginDto) throws Exception {
        User user = findUserByEmail(loginDto.getUserEmail());
        eventPublisher.publishEvent(new UserLogoutEvent(user));
        simpMessagingTemplate.convertAndSend("/topic/public/activeUsers", redisService.getActiveUsers());
    }

    /*
      Use Reference to update user info
     */

    public User updateUserInfo(Long id, UserDto userDto) throws IOException {
        User user = authRepo.findById(id).orElse(null);
        if (user != null) {

            if (userDto.getBio() != null) user.setBio(userDto.getBio());
            if (userDto.getPortfolio() != null) user.setPortfolio(userDto.getPortfolio());
            if (userDto.getSkills() != null) user.setSkills(userDto.getSkills());
            if (userDto.getLocation() != null) user.setLocation(userDto.getLocation());
            if (userDto.getHobbies() != null) user.setHobbies(userDto.getHobbies());
            if (userDto.getInstagram() != null) user.setInstagram(userDto.getInstagram());
            if (userDto.getProfessionalTitle() != null) user.setProfessionalTitle(userDto.getProfessionalTitle());
            if(userDto.getPublishedPostCount() != null) user.setPublishedPostCount(userDto.getPublishedPostCount());

            if (userDto.getProfileImage() != null) {
                byte[] profileImage = Helper.compressAndResizeImage(userDto.getProfileImage());
                user.setProfileImage(profileImage);
            }

            User updatedUser = authRepo.save(user);
            eventPublisher.publishEvent(new UserUpdateEvent(updatedUser));
            return updatedUser;
        }
        return null;
    }
}
