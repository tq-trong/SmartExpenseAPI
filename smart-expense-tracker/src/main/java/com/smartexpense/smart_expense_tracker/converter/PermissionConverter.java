package com.smartexpense.smart_expense_tracker.converter;

import com.smartexpense.smart_expense_tracker.dto.PermissionDTO;
import com.smartexpense.smart_expense_tracker.entity.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionConverter {
    public Permission toEntity(PermissionDTO dto) {
        Permission entity = new Permission();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    public PermissionDTO toDTO(Permission entity) {
        PermissionDTO dto = new PermissionDTO();
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        return dto;
    }
}
