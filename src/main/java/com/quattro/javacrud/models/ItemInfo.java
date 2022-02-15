package com.quattro.javacrud.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "items")
public class ItemInfo {

    private String id;

    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
