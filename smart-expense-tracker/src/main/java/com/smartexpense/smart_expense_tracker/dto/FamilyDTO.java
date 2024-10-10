package com.smartexpense.smart_expense_tracker.dto;

import java.util.Set;

public class FamilyDTO extends BaseDTO<FamilyDTO> {
    private String name;
    private Set<UserDTO> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }
}
