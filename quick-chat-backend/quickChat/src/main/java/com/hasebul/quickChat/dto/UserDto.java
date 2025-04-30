package com.hasebul.quickChat.dto;

import java.time.LocalDate;
import java.util.Objects;

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

    public UserDto() {
    }

    public UserDto(Long id, String userEmail, String userName, String professionalTitle, String password,
                   String location, LocalDate joinDate, String bio, String portfolio, String skills,
                   String hobbies, String instagram, Long publishedPostCount, byte[] profileImage) {
        this.id = id;
        this.userEmail = userEmail;
        this.userName = userName;
        this.professionalTitle = professionalTitle;
        this.password = password;
        this.location = location;
        this.joinDate = joinDate;
        this.bio = bio;
        this.portfolio = portfolio;
        this.skills = skills;
        this.hobbies = hobbies;
        this.instagram = instagram;
        this.publishedPostCount = publishedPostCount;
        this.profileImage = profileImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfessionalTitle() {
        return professionalTitle;
    }

    public void setProfessionalTitle(String professionalTitle) {
        this.professionalTitle = professionalTitle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public Long getPublishedPostCount() {
        return publishedPostCount;
    }

    public void setPublishedPostCount(Long publishedPostCount) {
        this.publishedPostCount = publishedPostCount;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
}
