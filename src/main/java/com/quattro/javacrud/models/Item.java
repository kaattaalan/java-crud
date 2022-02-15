package com.quattro.javacrud.models;

import com.quattro.javacrud.payload.request.ItemRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "items")
public class Item {

    @Id
    private String id;

    private String title;

    private String description;

    private Instant createdDate;

    private UserInfo userInfo;

    private List<UpdateDetails> updateDetails = new ArrayList<>();

    private ItemMetaData metaData = new ItemMetaData();

    public Item(ItemRequest request,UserInfo userInfo) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.createdDate = Instant.now();
        this.userInfo = userInfo;
    }

    public Item(){
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

    public List<UpdateDetails> getUpdateDetails() {
        return updateDetails;
    }

    public ItemMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(ItemMetaData metaData) {
        this.metaData = metaData;
    }
}
