package com.hasebul.quickChat.dto;

import java.time.LocalDateTime;

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

    public ChatMessageDto() {
    }

    public ChatMessageDto(String messageId, String senderId, String receiverId,
                          String senderName, String receiverName, String content,
                          LocalDateTime createdOn, String senderProfileImage, String receiverProfileImage) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.content = content;
        this.createdOn = createdOn;
        this.senderProfileImage = senderProfileImage;
        this.receiverProfileImage = receiverProfileImage;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getSenderProfileImage() {
        return senderProfileImage;
    }

    public void setSenderProfileImage(String senderProfileImage) {
        this.senderProfileImage = senderProfileImage;
    }

    public String getReceiverProfileImage() {
        return receiverProfileImage;
    }

    public void setReceiverProfileImage(String receiverProfileImage) {
        this.receiverProfileImage = receiverProfileImage;
    }
}
