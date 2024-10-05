package com.smartexpense.smart_expense_tracker.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDTO<T> {
    private String id;
    private LocalDateTime createdDate;

    private int page = 0;
    private int totalPage = 0;
    private int limitItem = 10;
    private List<T> listResult = new ArrayList<>();

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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int setTotalPage(long totalItems) {
        return (int) Math.ceil((double) totalItems / limitItem);
    }

    public int getLimitItem() {
        return limitItem;
    }

    public void setLimitItem(int limitItem) {
        this.limitItem = limitItem;
    }

    public List<T> getListResult() {
        return listResult;
    }

    public void setListResult(List<T> listResult) {
        this.listResult = listResult;
    }
}
