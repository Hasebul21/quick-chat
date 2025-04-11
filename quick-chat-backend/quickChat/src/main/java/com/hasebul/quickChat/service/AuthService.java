package com.hasebul.quickChat.service;

import com.hasebul.quickChat.dto.UserDto;
import com.hasebul.quickChat.model.Users;
import com.hasebul.quickChat.repository.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthRepo authRepo;

    public Users persistUser(UserDto userDto){
        Users user = new Users(userDto.getUserName(),userDto.getPassword() ,userDto.getUserEmail());
        return authRepo.save(user);
    }

    public List<Users> getAllUser(){
        List<Users> usersList = authRepo.findAll();
        return usersList;
    }

    public Users getUserById(Long id){
        Users user = authRepo.findById(id).get();
        return user;
    }

    public Users getUserByUserNameAndPassword(String useremail, String password) throws Exception {
        Optional<Users> users = authRepo.findByUserEmailAndPassword(useremail, password);
        return users.orElse(null);
    }

    public Users findUserByEmail(String email) {
        return authRepo.findByUserEmail(email).orElse(null);
    }
}
