package com.smartexpense.smart_expense_tracker.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartexpense.smart_expense_tracker.converter.PermissionConverter;
import com.smartexpense.smart_expense_tracker.converter.RoleConverter;
import com.smartexpense.smart_expense_tracker.dto.RoleDTO;
import com.smartexpense.smart_expense_tracker.entity.Permission;
import com.smartexpense.smart_expense_tracker.entity.Role;
import com.smartexpense.smart_expense_tracker.exception.AppException;
import com.smartexpense.smart_expense_tracker.exception.ErrorCode;
import com.smartexpense.smart_expense_tracker.repository.PermissionRepository;
import com.smartexpense.smart_expense_tracker.repository.RoleRepository;
import com.smartexpense.smart_expense_tracker.service.IRoleService;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleConverter roleConverter;

    @Autowired
    private PermissionConverter permissionConverter;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public RoleDTO create(RoleDTO dto) {
        Role role = roleConverter.toEntity(dto);

        Set<Permission> permissions = dto.getPermissions().stream()
                .map(permissionDTO -> permissionConverter.toEntity(permissionDTO))
                .collect(Collectors.toSet());

        role.setPermissions(permissions);
        roleRepository.save(role);

        return roleConverter.toDTO(role);
    }

    @Override
    public RoleDTO get(String id) {
        return roleConverter.toDTO(
                roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)));
    }
}
