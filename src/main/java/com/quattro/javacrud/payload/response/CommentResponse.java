package com.quattro.javacrud.payload.response;

import com.quattro.javacrud.models.Comment;
import com.quattro.javacrud.models.UserInfo;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "comments")
public class CommentResponse {

    private String id;

    private String title;

    private String description;

    private Instant createdDate;

    private UserInfo userInfo;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.title = comment.getTitle();
        this.description = comment.getDescription();
        this.createdDate = comment.getCreatedDate();
        this.userInfo = comment.getUserInfo();
    }

    public CommentResponse() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
