package com.hasebul.quickChat.utils;

import com.hasebul.quickChat.dto.ChatMessageDto;
import com.hasebul.quickChat.dto.PostDto;
import com.hasebul.quickChat.model.ChatMessage;
import com.hasebul.quickChat.model.Post;

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

    public static Post PostEntityToDto(PostDto dto) {
        if (dto == null) {
            return null;
        }

        Post post = new Post();
        post.setPostId(dto.getPostId());
        post.setContent(dto.getContent());
        post.setCreatorName(dto.getCreatorName());
        post.setCreatorEmail(dto.getCreatorEmail());
        post.setLikeCount(dto.getLikeCount());
        post.setDislikeCount(dto.getDislikeCount());

        return post;
    }
}
