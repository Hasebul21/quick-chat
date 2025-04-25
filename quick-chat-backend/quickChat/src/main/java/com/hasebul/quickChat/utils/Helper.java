package com.hasebul.quickChat.utils;

import com.hasebul.quickChat.dto.ChatMessageDto;
import com.hasebul.quickChat.dto.PostDto;
import com.hasebul.quickChat.dto.UserDto;
import com.hasebul.quickChat.model.ChatMessage;
import com.hasebul.quickChat.model.Post;
import com.hasebul.quickChat.model.User;

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
        post.setCreatorImage(dto.getCreatorImage());

        return post;
    }

    public static UserDto userEntityToDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUserEmail(user.getUserEmail());
        dto.setUserName(user.getUserName());
        dto.setPassword(user.getPassword());
        dto.setProfessionalTitle(user.getProfessionalTitle());
        dto.setLocation(user.getLocation());
        dto.setJoinDate(user.getJoinDate());
        dto.setBio(user.getBio());
        dto.setPortfolio(user.getPortfolio());
        dto.setSkills(user.getSkills());
        dto.setHobbies(user.getHobbies());
        dto.setInstagram(user.getInstagram());
        dto.setPublishedPostCount(user.getPublishedPostCount());
        dto.setProfileImage(user.getProfileImage());

        return dto;
    }

}
