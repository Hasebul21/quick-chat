package com.hasebul.quickChat.utils;

import com.hasebul.quickChat.dto.ChatMessageDto;
import com.hasebul.quickChat.model.ChatMessage;

public class Helper {

    public static ChatMessage mapChatDtoToChatEntity(ChatMessageDto dto) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageId(dto.getMessageId());
        chatMessage.setSenderId(dto.getSenderId());
        chatMessage.setSenderName(dto.getSenderName());
        chatMessage.setReceiverId(dto.getReceiverId());
        chatMessage.setReceiverName(dto.getReceiverName());
        chatMessage.setContent(dto.getContent());
        return chatMessage;
    }
}
