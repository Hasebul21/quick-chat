package com.hasebul.quickChat.controller;

import com.hasebul.quickChat.dto.ChatMessageDto;
import com.hasebul.quickChat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("/privateMessage")
    public void addMessageToQueue(@Payload ChatMessageDto chatMessage){
        chatMessageService.saveMessage(chatMessage);
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReciverId(),
                "/message/queue", chatMessage);
    }
}
