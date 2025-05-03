package com.hasebul.quickChat.controller;

import com.hasebul.quickChat.dto.ChatMessageDto;
import com.hasebul.quickChat.dto.RedisUserDto;
import com.hasebul.quickChat.dto.UserDto;
import com.hasebul.quickChat.model.ChatMessage;
import com.hasebul.quickChat.model.User;
import com.hasebul.quickChat.service.ChatMessageService;
import com.hasebul.quickChat.service.RedisService;
import com.hasebul.quickChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WebSocketUserController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    // endpoint will be app/addUser
    @MessageMapping("/chat/join")
    @SendTo("/topic/public/activeUsers")
    public List<RedisUserDto> handleUserJoin(@Payload UserDto userDto,
                           SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        User newUser = userService.findUserByEmail(userDto.getUserEmail());
        simpMessageHeaderAccessor.getSessionAttributes().put("userId",newUser.getId());
       // simpMessagingTemplate.convertAndSendToUser(newUser.getId().toString(), "public/activeUsers", redisService.getActiveUsers());
        return redisService.getActiveUsers();
    }

    @MessageMapping("/chat/leave")
    @SendTo("/topic/public/activeUsers")
    public List<RedisUserDto> handleUserLeave(@Payload UserDto userDto,
                                              SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception{

        String userId = (String) simpMessageHeaderAccessor.getSessionAttributes().get("userId");
        simpMessageHeaderAccessor.getSessionAttributes().remove("userId");
        User newUser = userService.findUserByEmail(userDto.getUserEmail());
        redisService.removeActiveUser(newUser);
        System.out.println("User left: " + userId);
        return redisService.getActiveUsers();
    }

    @MessageMapping("/chat/private/send")
    public void sendPrivateMessage(@Payload ChatMessageDto chatMessage) throws IOException {
        chatMessageService.saveMessage(chatMessage);
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiverId(),
                "/message/queue", chatMessage);
    }

    @GetMapping("/chatMessage/{senderId}/{receiverId}")
    public List<ChatMessage> getChatMessagesBetweenUsers(@PathVariable("senderId") String senderId,
                                                           @PathVariable("receiverId") String receiverId) throws IOException {
        return chatMessageService.chatMessageList(senderId, receiverId);
    }

}
