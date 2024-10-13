package com.smartexpense.smart_expense_tracker.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartexpense.smart_expense_tracker.dto.InvitationDTO;
import com.smartexpense.smart_expense_tracker.entity.Invitation;

@Component
public class InvitationConverter {
    @Autowired
    private StatusConverter statusConverter;

    @Autowired
    private UserConverter userConverter;

    public Invitation toEntity(InvitationDTO dto) {
        Invitation invitation = new Invitation();
        if (dto.getStatus() != null) invitation.setStatus(statusConverter.toEntity(dto.getStatus()));
        return invitation;
    }

    public InvitationDTO toDTO(Invitation entity) {
        InvitationDTO dto = new InvitationDTO();
        if (entity.getId() != null) dto.setId(entity.getId());

        dto.setInviter(userConverter.toDTO(entity.getInviter()));
        dto.setInvitee(entity.getInvitee().getUsername());
        dto.setStatus(statusConverter.toDTO(entity.getStatus()));
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public Invitation toEntity(Invitation entity, InvitationDTO dto) {
        entity.setStatus(statusConverter.toEntity(dto.getStatus()));

        return entity;
    }
}
