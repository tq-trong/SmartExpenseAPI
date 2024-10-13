package com.smartexpense.smart_expense_tracker.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
public class User extends Base {
    private String username;
    private String password;

    @ManyToMany
    private Set<Role> roles;

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
