package com.smartexpense.smart_expense_tracker.dto.response;

public class AuthenticationResponse {
    private boolean authenticated;
    private String token;

    public AuthenticationResponse() {}

    public AuthenticationResponse(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
