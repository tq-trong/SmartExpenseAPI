package com.smartexpense.smart_expense_tracker.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.smartexpense.smart_expense_tracker.dto.InvitationDTO;
import com.smartexpense.smart_expense_tracker.dto.request.SearchRequest;
import com.smartexpense.smart_expense_tracker.dto.response.ApiResponse;
import com.smartexpense.smart_expense_tracker.service.IInvitationService;

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

    @PutMapping("/{invitationId}")
    public ApiResponse<InvitationDTO> updateInvitation(
            @PathVariable String invitationId, @RequestBody InvitationDTO request) {
        ApiResponse<InvitationDTO> apiResponse = new ApiResponse<>();
        apiResponse.setResult(invitationService.updateInvitation(invitationId, request));

        return apiResponse;
    }

    @GetMapping
    public ApiResponse<Set<InvitationDTO>> getInvitation(
            @RequestParam("page") int page, @RequestParam(value = "search", required = false) String search) {
        ApiResponse<Set<InvitationDTO>> apiResponse = new ApiResponse<>();
        Pageable pageable = PageRequest.of(page - 1, apiResponse.getLimitItem());

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearch(search);

        Set<InvitationDTO> results = invitationService.getInvitations(searchRequest.getSearch(), pageable);
        apiResponse.setPage(page);
        apiResponse.setResult(results);
        long totalItems = invitationService.totalItems(searchRequest.getSearch(), pageable);

        apiResponse.setTotalPage(totalItems, apiResponse.getLimitItem());
        return apiResponse;
    }
}
