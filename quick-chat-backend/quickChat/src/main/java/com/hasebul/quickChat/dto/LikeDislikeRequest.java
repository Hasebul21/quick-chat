package com.hasebul.quickChat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LikeDislikeRequest {
    @JsonProperty("isLike")
    private boolean isLike;  // Field to capture the "isLike" parameter
    private int count;       // Field to capture the "count"

    // Getters and Setters
    public boolean isLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


