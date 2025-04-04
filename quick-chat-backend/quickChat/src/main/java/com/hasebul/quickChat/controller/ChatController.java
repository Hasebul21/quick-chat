package com.hasebul.quickChat.controller;

import com.hasebul.quickChat.dto.ChatMessageDto;
import com.hasebul.quickChat.model.ChatMessage;
import com.hasebul.quickChat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("/privateMessage")
    public void addMessageToQueue(@Payload ChatMessageDto chatMessage){
        chatMessageService.saveMessage(chatMessage);
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiverId(),
                "/message/queue", chatMessage);
    }

    @GetMapping("/chatMessage/{senderId}/{receiverId}")
    public List<ChatMessage> getAllChatBySenderAndReceiver(@PathVariable("senderId") String senderId,
                                                           @PathVariable("receiverId") String receiverId) throws IOException {
        return chatMessageService.chatMessageList(senderId, receiverId);
    }
}
