package com.smartexpense.smart_expense_tracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Status {
    @Id
    private String statusCode;

    private String description;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
