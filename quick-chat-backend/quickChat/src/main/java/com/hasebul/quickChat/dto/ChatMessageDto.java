package com.hasebul.quickChat.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ChatMessageDto {
    private String messageId;
    private String senderId;
    private String reciverId;
    private String content;
    private LocalDateTime createdOn;
    private MessageStatus status;
    private MessageType messageType;

    public ChatMessageDto() {
        this.messageId = UUID.randomUUID().toString();
        this.createdOn = LocalDateTime.now();
    }
}
