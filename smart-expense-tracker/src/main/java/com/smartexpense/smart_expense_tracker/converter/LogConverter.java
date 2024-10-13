package com.smartexpense.smart_expense_tracker.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartexpense.smart_expense_tracker.dto.LogDTO;
import com.smartexpense.smart_expense_tracker.entity.Log;

@Component
public class LogConverter {
    @Autowired
    private UserConverter userConverter;

    public Log toEntity(LogDTO dto) {
        Log entity = new Log();

        // entity.setUser(userConverter.toEntity(dto.getUser()));
        entity.setDescription(dto.getDescription());

        return entity;
    }

    public LogDTO toDTO(Log entity) {
        LogDTO dto = new LogDTO();

        if (entity.getId() != null) dto.setId(entity.getId());
        dto.setUser(userConverter.toDTO(entity.getUser()));
        dto.setDescription(entity.getDescription());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }
}
