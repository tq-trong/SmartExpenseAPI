package com.smartexpense.smart_expense_tracker.controller;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.smartexpense.smart_expense_tracker.dto.ExpenseDTO;
import com.smartexpense.smart_expense_tracker.dto.request.FilterRequest;
import com.smartexpense.smart_expense_tracker.dto.response.ApiResponse;
import com.smartexpense.smart_expense_tracker.service.IExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    @Autowired
    private IExpenseService expenseService;

    @PostMapping
    public ApiResponse<ExpenseDTO> createExpense(@RequestBody @Valid ExpenseDTO request) {
        ApiResponse<ExpenseDTO> apiResponse = new ApiResponse<>();
        apiResponse.setResult(expenseService.create(request));

        return apiResponse;
    }

    @PutMapping("/{expenseId}")
    public ApiResponse<ExpenseDTO> updateExpense(
            @PathVariable("expenseId") String expenseId, @RequestBody @Valid ExpenseDTO request) {
        ApiResponse<ExpenseDTO> apiResponse = new ApiResponse<>();
        apiResponse.setResult(expenseService.updateExpense(expenseId, request));

        return apiResponse;
    }

    @DeleteMapping("/{expenseId}")
    public ApiResponse<Void> deleteExpense(@PathVariable("expenseId") String expenseId) {
        expenseService.deleteExpense(expenseId);
        return new ApiResponse<>();
    }

    @GetMapping
    public ApiResponse<List<ExpenseDTO>> getExpenses(
            @RequestParam("page") int page,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "category", required = false) String category,
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
        filter.setCategory(category);
        filter.setSearch(search);

        ApiResponse<List<ExpenseDTO>> apiResponse = new ApiResponse<>();

        // Call service to get Expenses by Filter
        Pageable pageable = PageRequest.of(page - 1, apiResponse.getLimitItem());
        List<ExpenseDTO> expenses = expenseService.getExpenses(
                filter.getUsername(),
                filter.getStartDate(),
                filter.getEndDate(),
                filter.getCategory(),
                filter.getSearch(),
                pageable);

        apiResponse.setResult(expenses);
        apiResponse.setPage(page);

        long totalItems = expenseService.totalExpenses(
                filter.getUsername(),
                filter.getStartDate(),
                filter.getEndDate(),
                filter.getCategory(),
                filter.getSearch(),
                pageable);
        apiResponse.setTotalPage(totalItems, apiResponse.getLimitItem());

        return apiResponse;
    }

    @GetMapping("/categories")
    public ApiResponse<List<String>> findAllCategoryByFamily() {
        ApiResponse<List<String>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(expenseService.getAllCategoryByFamily());

        return apiResponse;
    }

    @GetMapping("/{expenseId}")
    public ApiResponse<ExpenseDTO> getExpenseById(@PathVariable("expenseId") String expenseId) {
        ApiResponse<ExpenseDTO> apiResponse = new ApiResponse<>();
        apiResponse.setResult(expenseService.get(expenseId));

        return apiResponse;
    }
}
