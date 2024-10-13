package com.smartexpense.smart_expense_tracker.converter;

import org.springframework.stereotype.Component;

import com.smartexpense.smart_expense_tracker.dto.StatusDTO;
import com.smartexpense.smart_expense_tracker.entity.Status;

@Component
public class StatusConverter {

    public Status toEntity(StatusDTO dto) {
        Status entity = new Status();

        entity.setStatusCode(dto.getStatusCode());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    public StatusDTO toDTO(Status entity) {
        StatusDTO dto = new StatusDTO();
        dto.setStatusCode(entity.getStatusCode());
        dto.setDescription(entity.getDescription());

        return dto;
    }
}
