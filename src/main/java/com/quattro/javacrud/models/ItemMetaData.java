package com.quattro.javacrud.models;

import java.util.HashSet;
import java.util.Set;

public class ItemMetaData {

    private Integer viewCount;

    private Set<String> upVoted = new HashSet<>();

    private Set<String> downVoted = new HashSet<>();

    public ItemMetaData() {
        this.viewCount = 0;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

}
