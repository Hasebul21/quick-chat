package com.hasebul.quickChat.dto;


import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatMessageDto {
    private String messageId;
    private String senderId;
    private String receiverId;
    private String senderName;
    private String receiverName;
    private String content;
    private LocalDateTime createdOn;
    private String senderProfileImage;
    private String receiverProfileImage;
}
