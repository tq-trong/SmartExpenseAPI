package com.smartexpense.smart_expense_tracker.converter;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartexpense.smart_expense_tracker.dto.FamilyDTO;
import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import com.smartexpense.smart_expense_tracker.entity.Family;
import com.smartexpense.smart_expense_tracker.entity.User;

@Component
public class FamilyConverter {

    @Autowired
    private UserConverter userConverter;

    public Family toEntity(FamilyDTO dto) {
        Family entity = new Family();
        entity.setName(dto.getName());

        return entity;
    }

    public FamilyDTO toDTO(Family entity) {
        FamilyDTO dto = new FamilyDTO();
        if (entity.getId() != null) dto.setId(entity.getId());

        dto.setName(entity.getName());
        Set<UserDTO> users = entity.getUser().stream().map(userConverter::toDTO).collect(Collectors.toSet());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public Family toEntity(Family entity, FamilyDTO dto) {
        Set<User> users = dto.getUsers().stream().map(userConverter::toEntity).collect(Collectors.toSet());

        entity.setUser(users);
        return entity;
    }
}
