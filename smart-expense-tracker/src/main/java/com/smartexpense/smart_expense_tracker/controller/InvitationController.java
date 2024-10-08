package com.smartexpense.smart_expense_tracker.controller;

import com.smartexpense.smart_expense_tracker.dto.InvitationDTO;
import com.smartexpense.smart_expense_tracker.dto.response.ApiResponse;
import com.smartexpense.smart_expense_tracker.service.IInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invitations")
public class InvitationController {
    @Autowired
    private IInvitationService invitationService;

    @PostMapping
    public ApiResponse<InvitationDTO> createInvitation(@RequestBody InvitationDTO request) {
        ApiResponse<InvitationDTO> apiResponse = new ApiResponse<>();
        apiResponse.setResult(invitationService.create(request));

        return apiResponse;
    }
}
