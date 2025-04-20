package com.hasebul.quickChat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "posts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

    @Id
    private String postId;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private String creatorName;

    @Field(type = FieldType.Keyword)
    private String creatorEmail;

    @Field(type = FieldType.Keyword) // Ensure proper format
    private String createdDate;

    @Field(type = FieldType.Keyword)
    private String updatedDate;

    @Field(type = FieldType.Keyword)
    private String creatorImage;

    @Field(type = FieldType.Integer)
    private int likeCount = 0;

    @Field(type = FieldType.Integer)
    private int dislikeCount = 0;

    // Constructors
    public Post() {}

    public Post(String content, String creatorName, String creatorEmail) {
        this.content = content;
        this.creatorName = creatorName;
        this.creatorEmail = creatorEmail;
        this.likeCount = 0;
        this.dislikeCount = 0;
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

    public String getCreatorImage() {
        return creatorImage;
    }

    public void setCreatorImage(String creatorImage) {
        this.creatorImage = creatorImage;
    }
}

