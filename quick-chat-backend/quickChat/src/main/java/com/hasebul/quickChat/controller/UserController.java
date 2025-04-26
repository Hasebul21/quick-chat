package com.hasebul.quickChat.controller;

import com.hasebul.quickChat.dto.RedisUserDto;
import com.hasebul.quickChat.dto.UserDto;
import com.hasebul.quickChat.model.User;
import com.hasebul.quickChat.service.RedisService;
import com.hasebul.quickChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    public List<RedisUserDto> getActiveUsers() {
        return redisService.getActiveUsers();
    }

    @PutMapping(value = "auth/users/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateInfo(
            @PathVariable("id") Long id,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestPart("professionalTitle") Optional<String> title,
            @RequestPart("location") Optional<String> location,
            @RequestPart("portfolio") Optional<String> portfolio,
            @RequestPart("instagram") Optional<String> instagram,
            @RequestPart("bio") Optional<String> bio,
            @RequestPart("skills") Optional<String> skills,
            @RequestPart("hobbies") Optional<String> hobbies
    ) throws IOException {
        UserDto userDto = new UserDto();
        title.ifPresent(userDto::setProfessionalTitle);
        location.ifPresent(userDto::setLocation);
        portfolio.ifPresent(userDto::setPortfolio);
        instagram.ifPresent(userDto::setInstagram);
        bio.ifPresent(userDto::setBio);
        skills.ifPresent(userDto::setSkills);
        hobbies.ifPresent(userDto::setHobbies);

        // Convert MultipartFile to byte[] if present
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                userDto.setProfileImage(profileImage.getBytes());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to convert profile image to bytes");
            }
        }

        User user = userService.updateUserInfo(id, userDto);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        return ResponseEntity.ok(user);
    }
}
