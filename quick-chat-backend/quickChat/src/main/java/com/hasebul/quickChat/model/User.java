package com.hasebul.quickChat.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String userEmail;
    private String userName;
    private String password;

    public User(String userName, String password, String userEmail){
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
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
