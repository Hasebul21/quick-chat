package com.hasebul.quickChat.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ChatMessage {
    private String messageId;
    private String senderName;
    private String reciverName;
    private String senderId;
    private String reciverId;
    private String content;
    private LocalDateTime createdOn;
    private MessageStatus status;
    private MessageType messageType;

    public ChatMessage() {
        this.messageId = UUID.randomUUID().toString();
        this.createdOn = LocalDateTime.now();
    }
}
