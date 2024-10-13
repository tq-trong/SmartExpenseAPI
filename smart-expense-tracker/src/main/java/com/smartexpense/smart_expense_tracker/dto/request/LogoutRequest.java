package com.smartexpense.smart_expense_tracker.dto.request;

public class LogoutRequest {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
