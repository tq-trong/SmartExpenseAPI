package com.smartexpense.smart_expense_tracker.service.impl;

import com.smartexpense.smart_expense_tracker.converter.RoleConverter;
import com.smartexpense.smart_expense_tracker.dto.RoleDTO;
import com.smartexpense.smart_expense_tracker.entity.Role;
import com.smartexpense.smart_expense_tracker.exception.AppException;
import com.smartexpense.smart_expense_tracker.exception.ErrorCode;
import com.smartexpense.smart_expense_tracker.repository.PermissionRepository;
import com.smartexpense.smart_expense_tracker.repository.RoleRepository;
import com.smartexpense.smart_expense_tracker.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleConverter roleConverter;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public RoleDTO create(RoleDTO dto) {
        Role role = new Role();
        role = roleConverter.toEntity(dto);

        var permissions = permissionRepository.findAllById(dto.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        roleRepository.save(role);

        return roleConverter.toDTO(role);
    }

    @Override
    public RoleDTO get(String id) {
        return roleConverter.toDTO(roleRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)));
    }
}
