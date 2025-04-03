package com.hasebul.quickChat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hasebul.quickChat.dto.MessageStatus;
import com.hasebul.quickChat.dto.MessageType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "chat_messages")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ChatMessage {
    @Id
    private String messageId;

    @Field(type = FieldType.Keyword)
    private String senderName;

    @Field(type = FieldType.Keyword)
    private String reciverName;

    @Field(type = FieldType.Keyword)
    private String senderId;

    @Field(type = FieldType.Keyword)
    private String reciverId;

    @Field(type = FieldType.Keyword)
    private String reciverEmail;

    @Field(type = FieldType.Keyword)
    private String senderEmail;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime createdOn;

    @Field(type = FieldType.Keyword)
    private MessageStatus status;

    @Field(type = FieldType.Keyword)
    private MessageType messageType;
}
