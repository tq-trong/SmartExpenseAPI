package com.smartexpense.smart_expense_tracker.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int code = 1000;
    private String message;
    private T result;

    private int page = 0;
    private int totalPage = 0;
    private int limitItem = 10;

    public ApiResponse() {}

    public ApiResponse(int code, String message, T result, int page, int totalPage, int limitItem) {
        this.code = code;
        this.message = message;
        this.result = result;
        this.page = page;
        this.totalPage = totalPage;
        this.limitItem = limitItem;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
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

    public void setTotalPage(long totalItems, long limit) {
        this.totalPage = (int) Math.ceil((double) totalItems / limit);
    }

    public int getLimitItem() {
        return limitItem;
    }

    public void setLimitItem(int limitItem) {
        this.limitItem = limitItem;
    }
}
