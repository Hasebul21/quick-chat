package com.hasebul.quickChat.controller;

import com.hasebul.quickChat.dto.ChatMessageDto;
import com.hasebul.quickChat.dto.UserDto;
import com.hasebul.quickChat.service.AuthService;
import com.hasebul.quickChat.service.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserSession userSession;

    @Autowired
    private AuthService authService;

    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public UserDto addNewUser(@Payload UserDto userDto,
                              SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        UserDto newUserDto = new UserDto();
        newUserDto.setId(userDto.getId());
        newUserDto.setUserName(userDto.getUserName());
        newUserDto.setUserEmail(userDto.getUserEmail());
        simpMessageHeaderAccessor.getSessionAttributes().put("username",newUserDto.getId());
        simpMessagingTemplate.convertAndSend("/topic/public/activeUsers", authService.getAllUser());
        simpMessagingTemplate.convertAndSend("/topic/public/loggedInUser", newUserDto);
        return newUserDto;
    }


    @MessageMapping("/removeUser")
    @SendTo("/topic/public")
    public String removeUser(@Payload ChatMessageDto chatMessage,
                             SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception{

        String userId = (String) simpMessageHeaderAccessor.getSessionAttributes().get("username");
        simpMessageHeaderAccessor.getSessionAttributes().remove("username");
        /*
            Find the user By userId querying in elastic search
         */
        // userSession.removeUserFromSession(userSession.findUserById(userId));
        /*
           Find the user name By querying in elastic search
         */
        return userId + " has leave from the chat";
    }

}
