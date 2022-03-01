package com.quattro.javacrud.models;

import com.quattro.javacrud.payload.request.CommentRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    @DBRef
    private UserInfo userInfo;

    private String itemId;

    private String title;

    private String description;

    private Instant createdDate;

    private Boolean deleted;

    public String getId() {
        return id;
    }

    public Comment(CommentRequest request) {
        this.userInfo = new UserInfo();
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.createdDate = Instant.now();
        this.userInfo.setId(request.getUserId());
        this.itemId = request.getItemId();
        this.deleted = false;
    }

    public Comment() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
