package com.smartexpense.smart_expense_tracker.service;

import com.smartexpense.smart_expense_tracker.dto.InvitationDTO;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface IInvitationService extends IBaseService<InvitationDTO>{
    Set<InvitationDTO> getInvitations(String search, Pageable pageable);
    InvitationDTO updateInvitation(String invitationId, InvitationDTO dto);
    long totalItems(String username, Pageable pageable);
}
