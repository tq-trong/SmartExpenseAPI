package com.smartexpense.smart_expense_tracker.dto;

import java.time.LocalDateTime;

public abstract class BaseDTO<T> {
    private String id;
    private LocalDateTime createdDate;

    public BaseDTO() {
        this.createdDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
