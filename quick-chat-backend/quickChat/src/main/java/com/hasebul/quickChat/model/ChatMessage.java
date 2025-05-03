package com.hasebul.quickChat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hasebul.quickChat.dto.MessageStatus;
import com.hasebul.quickChat.dto.MessageType;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "chat_messages")
public class ChatMessage {
    @Id
    private String messageId;

    @Field(type = FieldType.Keyword)
    private String senderName;

    @Field(type = FieldType.Keyword)
    private String receiverName;

    @Field(type = FieldType.Keyword)
    private String senderId;

    @Field(type = FieldType.Keyword)
    private String receiverId;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private String createdOn;

    @Field(type = FieldType.Keyword)
    private String senderProfileImage;

    @Field(type = FieldType.Keyword)
    private String receiverProfileImage;

    public ChatMessage() {
    }

    public ChatMessage(String messageId, String senderName, String receiverName, String senderId, String receiverId,
                       String content, String createdOn, String senderProfileImage, String receiverProfileImage) {
        this.messageId = messageId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.senderId = senderId;
        this.receiverId = receiverId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
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
