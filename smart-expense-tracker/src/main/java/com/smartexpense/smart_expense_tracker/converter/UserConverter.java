package com.smartexpense.smart_expense_tracker.converter;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import com.smartexpense.smart_expense_tracker.entity.User;

@Component
public class UserConverter {
    @Autowired
    private RoleConverter roleConverter;

    public User toEntity(UserDTO dto) {
        User entity = new User();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());

        return entity;
    }

    public UserDTO toDTO(User entity) {
        UserDTO dto = new UserDTO();
        if (entity.getId() != null) dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setRoles(entity.getRoles().stream().map(roleConverter::toDTO).collect(Collectors.toSet()));
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public User toEntity(User entity, UserDTO dto) {
        return entity;
    }
}
