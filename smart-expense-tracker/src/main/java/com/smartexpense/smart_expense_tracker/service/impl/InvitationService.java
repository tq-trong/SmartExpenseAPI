package com.smartexpense.smart_expense_tracker.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smartexpense.smart_expense_tracker.converter.FamilyConverter;
import com.smartexpense.smart_expense_tracker.converter.InvitationConverter;
import com.smartexpense.smart_expense_tracker.converter.UserConverter;
import com.smartexpense.smart_expense_tracker.dto.FamilyDTO;
import com.smartexpense.smart_expense_tracker.dto.InvitationDTO;
import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import com.smartexpense.smart_expense_tracker.entity.Family;
import com.smartexpense.smart_expense_tracker.entity.Invitation;
import com.smartexpense.smart_expense_tracker.entity.Status;
import com.smartexpense.smart_expense_tracker.entity.User;
import com.smartexpense.smart_expense_tracker.enums.InvitationStatus;
import com.smartexpense.smart_expense_tracker.enums.Roles;
import com.smartexpense.smart_expense_tracker.exception.AppException;
import com.smartexpense.smart_expense_tracker.exception.ErrorCode;
import com.smartexpense.smart_expense_tracker.repository.FamilyRepository;
import com.smartexpense.smart_expense_tracker.repository.InvitationRepository;
import com.smartexpense.smart_expense_tracker.repository.StatusRepository;
import com.smartexpense.smart_expense_tracker.repository.UserRepository;
import com.smartexpense.smart_expense_tracker.service.IFamilyService;
import com.smartexpense.smart_expense_tracker.service.IInvitationService;
import com.smartexpense.smart_expense_tracker.service.IUserService;

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
    private FamilyRepository familyRepository;

    @Autowired
    private InvitationConverter invitationConverter;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFamilyService familyService;

    @Autowired
    private FamilyConverter familyConverter;

    @Override
    public InvitationDTO create(InvitationDTO dto) {
        Invitation invitation = invitationConverter.toEntity(dto);

        User inviter = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (inviter.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase(Roles.MEMBER.name()))) {
            throw new AppException(ErrorCode.INVITER_CANNOT_INVITE);
        }

        User invitee = userRepository
                .findByUsername(dto.getInvitee())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (familyRepository.findByUser(invitee.getUsername()).isPresent())
            throw new AppException(ErrorCode.INVITEE_HAS_ALREADY_FAMILY);

        Status status = statusRepository
                .findByStatusCode(InvitationStatus.PENDING.name())
                .orElseThrow(() -> new AppException(ErrorCode.STATUS_NOT_FOUND));

        invitation.setInviter(inviter);
        invitation.setInvitee(invitee);
        invitation.setStatus(status);

        List<Invitation> existingInvitations =
                invitationRepository.findByInviterAndInvitee(invitation.getInviter(), invitee);

        // Kiểm tra nếu danh sách không rỗng và trạng thái là PENDING
        if (existingInvitations.stream()
                .anyMatch(inv -> Objects.equals(inv.getStatus().getStatusCode(), InvitationStatus.PENDING.name()))) {
            throw new AppException(ErrorCode.ALREADY_INVITED);
        }

        invitationRepository.save(invitation);

        return invitationConverter.toDTO(invitation);
    }

    @Override
    public InvitationDTO get(String id) {
        return invitationConverter.toDTO(
                invitationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public Set<InvitationDTO> getInvitations(String search, Pageable pageable) {
        return getPageInvitation(search, pageable).stream()
                .map(invitationConverter::toDTO)
                .collect(Collectors.toSet());
    }

    public Page<Invitation> getPageInvitation(String search, Pageable pageable) {
        User user;
        user = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Page<Invitation> invitations;

        invitations = invitationRepository.findByInviteeAndInviterUsername(user, search, pageable);

        return invitations;
    }

    @Override
    public InvitationDTO updateInvitation(String id, InvitationDTO dto) {
        String oldStatus = invitationRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVITATION_NOT_EXISTED))
                .getStatus()
                .getStatusCode();

        Invitation invitation = invitationConverter.toEntity(
                invitationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVITATION_NOT_EXISTED)),
                dto);
        User invitee = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!invitation.getInvitee().equals(invitee)) throw new AppException(ErrorCode.PERMISSION_INVALID);

        if (!oldStatus.equals(InvitationStatus.PENDING.name()))
            throw new AppException(ErrorCode.UPDATE_PENDING_INVITATION);

        Optional<Family> optionalFamily =
                familyRepository.findByUser(invitation.getInvitee().getUsername());

        if (optionalFamily.isEmpty()) {
            if (invitation.getStatus().getStatusCode().equals(InvitationStatus.ACCEPTED.name())) {
                createFamily(userConverter.toDTO(invitation.getInviter()), userConverter.toDTO(invitee));
            }
        } else if (invitation.getStatus().getStatusCode().equals(InvitationStatus.ACCEPTED.name())) {
            throw new AppException(ErrorCode.UPDATE_ACCEPTED_INVITATION);
        }

        invitationRepository.save(invitation);
        return invitationConverter.toDTO(invitation);
    }

    public void createFamily(UserDTO inviter, UserDTO invitee) {
        Optional<Family> optionalFamily = familyRepository.findByUser(inviter.getUsername());

        if (optionalFamily.isEmpty()) { // If user haven't already family then create new family

            FamilyDTO familyDTO = new FamilyDTO();

            Set<UserDTO> userDTOS = new HashSet<>();
            userDTOS.add(inviter);
            userDTOS.add(invitee);

            familyDTO.setName("Your family");
            familyDTO.setUsers(userDTOS);

            familyService.createFamily(familyDTO);
            userService.updateUserRole(inviter, Roles.ADMIN.name());
            userService.updateUserRole(invitee, Roles.MEMBER.name());
        } else { // Else update invitee to join family
            Family family = optionalFamily.get();
            FamilyDTO familyDTO = new FamilyDTO();

            Set<UserDTO> userDTOS = new HashSet<>();
            userDTOS.add(invitee);

            familyDTO.setName("Your family");
            familyDTO.setUsers(userDTOS);

            familyService.updateFamily(family.getId(), invitee.getUsername());
            userService.updateUserRole(invitee, Roles.MEMBER.name());
        }
    }

    @Override
    public long totalItems(String username, Pageable pageable) {
        return getPageInvitation(username, pageable).getTotalElements();
    }
}
