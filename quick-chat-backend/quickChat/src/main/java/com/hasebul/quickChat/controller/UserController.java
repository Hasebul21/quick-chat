package com.hasebul.quickChat.controller;

import com.hasebul.quickChat.dto.ChatMessage;
import com.hasebul.quickChat.dto.User;
import com.hasebul.quickChat.service.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserSession userSession;

    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public ChatMessage addNewUser(@Payload ChatMessage chatMessage,
                           SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        User newUser = new User();
        newUser.setUserName(chatMessage.getSenderName());
        chatMessage.setSenderId(newUser.getUserId());
        simpMessageHeaderAccessor.getSessionAttributes().put("username",chatMessage.getSenderId());
        userSession.addUserToSession(newUser);
        chatMessage.setContent(chatMessage.getSenderName() + " has Joined the chat");
        return chatMessage;
    }

    @MessageMapping("/removeUser")
    @SendTo("/topic/public")
    public String removeUser(@Payload ChatMessage chatMessage,
                             SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception{

        String userId = (String) simpMessageHeaderAccessor.getSessionAttributes().get("username");
        simpMessageHeaderAccessor.getSessionAttributes().remove("username");
        /*
            Find the user By userId querying in elastic search
         */
        userSession.removeUserFromSession(userSession.findUserById(userId));
        /*
           Find the user name By querying in elastic search
         */
        return userId + " has leave from the chat";
    }
}
