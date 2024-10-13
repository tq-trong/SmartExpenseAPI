package com.smartexpense.smart_expense_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartexpense.smart_expense_tracker.dto.PermissionDTO;
import com.smartexpense.smart_expense_tracker.dto.response.ApiResponse;
import com.smartexpense.smart_expense_tracker.service.IPermissionService;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionDTO> createPermission(@RequestBody PermissionDTO dto) {
        ApiResponse<PermissionDTO> apiResponse = new ApiResponse<>();
        apiResponse.setResult(permissionService.create(dto));

        return apiResponse;
    }
}
