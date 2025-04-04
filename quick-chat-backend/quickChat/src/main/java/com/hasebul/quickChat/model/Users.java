package com.hasebul.quickChat.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue
    private Long id;
    private String userEmail;
    private String userName;
    private String password;

    public Users(String userName, String password, String userEmail){
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
    }
}
