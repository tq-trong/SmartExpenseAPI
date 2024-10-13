package com.smartexpense.smart_expense_tracker.dto;

public class LogDTO extends BaseDTO<LogDTO> {
    private UserDTO user;
    private String description;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
