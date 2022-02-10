package com.quattro.javacrud.models;

import java.time.Instant;

public class UpdateDetails {

    private String userId;

    private Instant modifiedDate;

    public UpdateDetails(String userId, Instant modifiedDate) {
        this.userId = userId;
        this.modifiedDate = modifiedDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
