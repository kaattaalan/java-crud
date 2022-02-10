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

    private String userId;

    private List<UpdateDetails> updateDetails = new ArrayList<>();

    public Item(ItemRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.createdDate = Instant.now();
        this.userId = request.getUserId();
    }

    public Item(ItemRequest request,Instant updateDate){
        this.id = request.getId();
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.updateDetails.add(new UpdateDetails(request.getUserId(),updateDate));
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
