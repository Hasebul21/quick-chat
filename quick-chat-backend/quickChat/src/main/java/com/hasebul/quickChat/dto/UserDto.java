package com.hasebul.quickChat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String userEmail;
    private String userName;
    private String professionalTitle;
    private String password;
    private String location;
    private LocalDate joinDate;
    private String bio;
    private String portfolio;
    private String skills;
    private String hobbies;
    private String instagram;
    private Long publishedPostCount;
    private byte[] profileImage;
}
