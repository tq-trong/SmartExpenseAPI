package com.smartexpense.smart_expense_tracker.dto;

import jakarta.validation.constraints.Size;

import java.util.Set;

public class UserDTO extends BaseDTO<UserDTO>{
    @Size(min = 4, message = "USERNAME_INVALID")
    private String username;

    @Size(min = 6, message = "PASSWORD_INVALID")
    private String password;

    private Set<String> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
