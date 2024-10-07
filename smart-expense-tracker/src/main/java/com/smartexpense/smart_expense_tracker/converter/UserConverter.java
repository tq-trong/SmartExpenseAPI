package com.smartexpense.smart_expense_tracker.converter;

import com.smartexpense.smart_expense_tracker.dto.RoleDTO;
import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import com.smartexpense.smart_expense_tracker.entity.Role;
import com.smartexpense.smart_expense_tracker.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;


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
        if(entity.getId() != null) dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setRoles(
                entity.getRoles().stream()
                        .map(Role::getName)  // Chuyển đổi từ Role entity sang tên Role (String)
                        .collect(Collectors.toSet())
        );
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public User toEntity(User entity, UserDTO dto) {
        return entity;
    }
}
