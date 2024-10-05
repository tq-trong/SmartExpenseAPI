package com.smartexpense.smart_expense_tracker.entity;

import jakarta.persistence.Entity;

import java.util.Set;

@Entity
public class User extends Base{
    private String username;
    private String password;
    private Set<String> roles;

    // Getters and Setters
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
