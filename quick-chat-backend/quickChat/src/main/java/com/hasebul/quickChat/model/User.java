package com.hasebul.quickChat.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
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
    public User(String userName, String password, String userEmail){
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.joinDate = LocalDate.now();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof User))
            return false;
        User otherUser = (User) obj;
        return id != null && id.equals(otherUser.id);
    }
}
