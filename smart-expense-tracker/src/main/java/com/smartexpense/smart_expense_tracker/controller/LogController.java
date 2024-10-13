package com.smartexpense.smart_expense_tracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartexpense.smart_expense_tracker.dto.LogDTO;
import com.smartexpense.smart_expense_tracker.dto.request.FilterRequest;
import com.smartexpense.smart_expense_tracker.dto.response.ApiResponse;
import com.smartexpense.smart_expense_tracker.service.ILogService;

@RestController
@RequestMapping("/logs")
public class LogController {
    @Autowired
    private ILogService logService;

    @GetMapping
    public ApiResponse<List<LogDTO>> getLogs(
            @RequestParam("page") int page,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "search", required = false) String search) {
        LocalDate startDate = null;
        LocalDate endDate = null;

        // Parse values of startDate and endDate if not null or empty
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = LocalDate.parse(startDateStr);
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = LocalDate.parse(endDateStr);
        }

        // Create Filter
        FilterRequest filter = new FilterRequest();
        filter.setUsername(username);
        filter.setStartDate(startDate);
        filter.setEndDate(endDate);
        filter.setSearch(search);

        ApiResponse<List<LogDTO>> apiResponse = new ApiResponse<>();

        // Call service to get Expenses by Filter
        Pageable pageable = PageRequest.of(page - 1, apiResponse.getLimitItem());
        List<LogDTO> logs = logService.getLogs(
                filter.getUsername(), filter.getStartDate(), filter.getEndDate(), filter.getSearch(), pageable);

        apiResponse.setResult(logs);
        apiResponse.setPage(page);

        long totalItems = logService.totalLogs(
                filter.getUsername(), filter.getStartDate(), filter.getEndDate(), filter.getSearch(), pageable);
        apiResponse.setTotalPage(totalItems, apiResponse.getLimitItem());

        return apiResponse;
    }
}
