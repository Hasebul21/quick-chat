package com.hasebul.quickChat.dto;

public class PostFilterDTO {

    private String creatorName;
    private String content;
    private RangeFilter likeCount;
    private RangeFilter dislikeCount;
    private DateRangeFilter createdDate;
    private DateRangeFilter updatedDate;

    // Getters and Setters
    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public RangeFilter getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(RangeFilter likeCount) {
        this.likeCount = likeCount;
    }

    public RangeFilter getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(RangeFilter dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public DateRangeFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateRangeFilter createdDate) {
        this.createdDate = createdDate;
    }

    public DateRangeFilter getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(DateRangeFilter updatedDate) {
        this.updatedDate = updatedDate;
    }

    // Inner class for numeric range filters (likeCount, dislikeCount)
    public static class RangeFilter {
        private Integer gte;
        private Integer lte;

        public Integer getGte() {
            return gte;
        }

        public void setGte(Integer gte) {
            this.gte = gte;
        }

        public Integer getLte() {
            return lte;
        }

        public void setLte(Integer lte) {
            this.lte = lte;
        }
    }

    // Inner class for date range filters (createdDate, updatedDate)
    public static class DateRangeFilter {
        private String gte; // ISO 8601 format recommended (e.g., "2024-01-01T00:00:00Z")
        private String lte;

        public String getGte() {
            return gte;
        }

        public void setGte(String gte) {
            this.gte = gte;
        }

        public String getLte() {
            return lte;
        }

        public void setLte(String lte) {
            this.lte = lte;
        }
    }

    @Override
    public String toString() {
        return "PostFilterDTO{" +
                "creatorName='" + creatorName + '\'' +
                ", content='" + content + '\'' +
                ", likeCount=" + likeCount +
                ", dislikeCount=" + dislikeCount +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
