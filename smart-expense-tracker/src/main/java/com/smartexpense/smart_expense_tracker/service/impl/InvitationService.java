package com.smartexpense.smart_expense_tracker.service.impl;

import com.smartexpense.smart_expense_tracker.converter.InvitationConverter;
import com.smartexpense.smart_expense_tracker.converter.UserConverter;
import com.smartexpense.smart_expense_tracker.dto.InvitationDTO;
import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import com.smartexpense.smart_expense_tracker.entity.Invitation;
import com.smartexpense.smart_expense_tracker.entity.Status;
import com.smartexpense.smart_expense_tracker.entity.User;
import com.smartexpense.smart_expense_tracker.exception.AppException;
import com.smartexpense.smart_expense_tracker.exception.ErrorCode;
import com.smartexpense.smart_expense_tracker.repository.InvitationRepository;
import com.smartexpense.smart_expense_tracker.repository.StatusRepository;
import com.smartexpense.smart_expense_tracker.repository.UserRepository;
import com.smartexpense.smart_expense_tracker.service.IInvitationService;
import com.smartexpense.smart_expense_tracker.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvitationService implements IInvitationService {
    private static final Logger log = LoggerFactory.getLogger(InvitationService.class);
    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvitationConverter invitationConverter;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private IUserService userService;

    @Override
    public InvitationDTO create(InvitationDTO dto) {
        Invitation invitation = new Invitation();

        User inviter = userRepository.findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        User invitee = userRepository.findByUsername(dto.getInvitee())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Status status = new Status();
        status = statusRepository.findByStatusCode(com.smartexpense.smart_expense_tracker.enums.Status.PENDING.name())
                .orElseThrow(() -> new AppException(ErrorCode.STATUS_NOT_FOUND));

        invitation.setInviter(inviter);
        invitation.setInvitee(invitee);
        invitation.setStatus(status);

        Optional<Invitation> existingInvitation = invitationRepository
                .findByInviterAndInvitee(invitation.getInviter(), invitee);
        if (existingInvitation.isPresent())
            throw new AppException(ErrorCode.ALREADY_INVITED);

        invitationRepository.save(invitation);
        return invitationConverter.toDTO(invitation);

    }

    @Override
    public InvitationDTO get(String id) {
        return null;
    }
}
