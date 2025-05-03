package com.hasebul.quickChat.model;
import javax.persistence.Id; // Add this instead
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String userEmail;

    @Column(nullable = false, updatable = false)
    private String userName;

    @Column(nullable = false, updatable = false)
    private String password;

    private String professionalTitle;
    private String location;

    @Column(updatable = false)
    private LocalDate joinDate;

    @Column(length = 300)
    private String bio;

    private String portfolio;

    @Column(length = 100)
    private String skills;

    @Column(length = 100)
    private String hobbies;

    private String instagram;
    private Long publishedPostCount;

    @Lob
    private byte[] profileImage;

    public User() {
    }

    public User(String userName, String password, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.joinDate = LocalDate.now();
    }

    // Getters and Setters

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfessionalTitle() {
        return professionalTitle;
    }

    public void setProfessionalTitle(String professionalTitle) {
        this.professionalTitle = professionalTitle;
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

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User otherUser = (User) obj;
        return id != null && id.equals(otherUser.id);
    }
}
