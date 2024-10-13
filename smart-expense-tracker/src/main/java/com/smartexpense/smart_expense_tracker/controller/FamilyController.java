package com.smartexpense.smart_expense_tracker.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import com.smartexpense.smart_expense_tracker.dto.request.SearchRequest;
import com.smartexpense.smart_expense_tracker.dto.response.ApiResponse;
import com.smartexpense.smart_expense_tracker.service.IFamilyService;
import com.smartexpense.smart_expense_tracker.service.IUserService;

@RestController
@RequestMapping("/families")
public class FamilyController {
    @Autowired
    private IFamilyService familyService;

    @Autowired
    private IUserService userService;

    @DeleteMapping("/{username}")
    public ApiResponse<Void> deleteMember(@PathVariable("username") String username) {
        familyService.deleteMember(username);

        return new ApiResponse<>();
    }

    @GetMapping
    public ApiResponse<Set<UserDTO>> getMembersInFamily(
            @RequestParam("page") int page, @RequestParam(value = "search", required = false) String search) {
        ApiResponse<Set<UserDTO>> apiResponse = new ApiResponse<>();

        Pageable pageable = PageRequest.of(page - 1, apiResponse.getLimitItem());

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearch(search);

        Set<UserDTO> results = userService.getMembers(searchRequest.getSearch(), pageable);

        apiResponse.setPage(page);
        apiResponse.setResult(results);

        long totalItems = userService.totalMembers(searchRequest.getSearch(), pageable);
        apiResponse.setTotalPage(totalItems, apiResponse.getLimitItem());

        return apiResponse;
    }
}
