package com.smartexpense.smart_expense_tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartexpense.smart_expense_tracker.converter.PermissionConverter;
import com.smartexpense.smart_expense_tracker.dto.PermissionDTO;
import com.smartexpense.smart_expense_tracker.entity.Permission;
import com.smartexpense.smart_expense_tracker.exception.AppException;
import com.smartexpense.smart_expense_tracker.exception.ErrorCode;
import com.smartexpense.smart_expense_tracker.repository.PermissionRepository;
import com.smartexpense.smart_expense_tracker.service.IPermissionService;

@Service
public class PermissionService implements IPermissionService {
    @Autowired
    private PermissionConverter permissionConverter;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public PermissionDTO create(PermissionDTO dto) {
        Permission permission = new Permission();
        permission = permissionConverter.toEntity(dto);

        permissionRepository.save(permission);

        return permissionConverter.toDTO(permission);
    }

    @Override
    public PermissionDTO get(String id) {
        return permissionConverter.toDTO(
                permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_INVALID)));
    }
}
