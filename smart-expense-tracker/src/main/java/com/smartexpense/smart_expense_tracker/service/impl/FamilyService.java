package com.smartexpense.smart_expense_tracker.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.smartexpense.smart_expense_tracker.converter.FamilyConverter;
import com.smartexpense.smart_expense_tracker.converter.UserConverter;
import com.smartexpense.smart_expense_tracker.dto.FamilyDTO;
import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import com.smartexpense.smart_expense_tracker.entity.Family;
import com.smartexpense.smart_expense_tracker.entity.Role;
import com.smartexpense.smart_expense_tracker.entity.User;
import com.smartexpense.smart_expense_tracker.enums.Roles;
import com.smartexpense.smart_expense_tracker.exception.AppException;
import com.smartexpense.smart_expense_tracker.exception.ErrorCode;
import com.smartexpense.smart_expense_tracker.repository.FamilyRepository;
import com.smartexpense.smart_expense_tracker.repository.RoleRepository;
import com.smartexpense.smart_expense_tracker.repository.UserRepository;
import com.smartexpense.smart_expense_tracker.service.IFamilyService;
import com.smartexpense.smart_expense_tracker.service.IUserService;

@Service
public class FamilyService implements IFamilyService {
    @Autowired
    private FamilyConverter familyConverter;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public FamilyDTO updateFamily(String familyId, String username) {
        Family family = familyRepository.findById(familyId).orElseThrow();

        Set<User> users = family.getUser();

        users.add(userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));

        family.setUser(users);

        familyRepository.save(family);

        return familyConverter.toDTO(family);
    }

    @Override
    public FamilyDTO createFamily(FamilyDTO dto) {
        Family family = familyConverter.toEntity(dto);
        Set<String> userIds = dto.getUsers().stream().map(UserDTO::getId).collect(Collectors.toSet());
        Set<User> setUsers = userRepository.findAllByIdIn(userIds);
        family.setUser(setUsers);

        familyRepository.save(family);

        return familyConverter.toDTO(family);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteMember(String username) {
        User member =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        User admin = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Family familyOfAdmin = familyRepository
                .findByUser(admin.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.FAMILY_NOT_EXISTED));
        if (familyOfAdmin.getUser().contains(member)) {
            familyOfAdmin.getUser().remove(member);
            Set<Role> role = new HashSet<>();
            Role userRole = roleRepository
                    .findByName(Roles.USER.name())
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
            role.add(userRole);
            member.setRoles(role);

            userRepository.save(member);
            familyRepository.save(familyOfAdmin);
        } else throw new AppException(ErrorCode.PERMISSION_INVALID);
    }
}
