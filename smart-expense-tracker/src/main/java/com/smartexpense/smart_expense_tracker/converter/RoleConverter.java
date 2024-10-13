package com.smartexpense.smart_expense_tracker.converter;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartexpense.smart_expense_tracker.dto.RoleDTO;
import com.smartexpense.smart_expense_tracker.entity.Role;

@Component
public class RoleConverter {
    @Autowired
    private PermissionConverter permissionConverter;

    public Role toEntity(RoleDTO dto) {
        Role entity = new Role();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    public RoleDTO toDTO(Role entity) {
        RoleDTO dto = new RoleDTO();
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        dto.setPermissions(
                entity.getPermissions().stream().map(permissionConverter::toDTO).collect(Collectors.toSet()));

        return dto;
    }
}
