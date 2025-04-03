package com.hasebul.quickChat.service;

import com.hasebul.quickChat.dto.ChatMessageDto;
import com.hasebul.quickChat.model.ChatMessage;
import com.hasebul.quickChat.repository.ChatMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    public void saveMessage(ChatMessageDto chatMessageDto){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageId(chatMessageDto.getMessageId());
        chatMessage.setSenderId(chatMessageDto.getSenderId());
        chatMessage.setReciverId(chatMessageDto.getReciverId());
        chatMessage.setContent(chatMessageDto.getContent());
        chatMessage.setMessageType(chatMessageDto.getMessageType());
        chatMessageRepo.save(chatMessage);
   }

}
