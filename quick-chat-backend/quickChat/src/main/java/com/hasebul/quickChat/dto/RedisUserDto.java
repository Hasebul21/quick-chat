package com.hasebul.quickChat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisUserDto implements Serializable {
    private Long id;
    private String userEmail;
    private String userName;
    private String professionalTitle;
    private String location;
    private String bio;
    private String portfolio;
    private String skills;
    private String hobbies;
    private String instagram;
    private Long publishedPostCount;
    private byte[] profileImage;
}
