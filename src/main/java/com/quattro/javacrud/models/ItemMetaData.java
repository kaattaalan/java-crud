package com.quattro.javacrud.models;

public class ItemMetaData {

    private Integer viewCount;

    private Integer votes;

    public ItemMetaData() {
        this.viewCount = 0;
        this.votes = 0;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
