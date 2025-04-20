package com.hasebul.quickChat.dto;

import com.hasebul.quickChat.model.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostDto {

    private String postId;
    private String content;
    private String creatorName;
    private String creatorEmail;
    private String createdDate;  // Store as String in the DTO
    private String updatedDate;  // Store as String in the DTO
    private int likeCount;
    private int dislikeCount;
    private String creatorImage;

    // Constructors
    public PostDto() {}

    public PostDto(String postId, String content,
                   String creatorName, String creatorEmail,
                   String createdDate, String updatedDate,
                   int likeCount, int dislikeCount, String creatorImage) {
        this.postId = postId;
        this.content = content;
        this.creatorName = creatorName;
        this.creatorEmail = creatorEmail;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.creatorImage = creatorImage;
    }

    // Getters and Setters

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }


    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatorImage() {
        return creatorImage;
    }

    public void setCreatorImage(String creatorImage) {
        this.creatorImage = creatorImage;
    }
}