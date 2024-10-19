package com.smartexpense.smart_expense_tracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.smartexpense.smart_expense_tracker.dto.response.ApiResponse;
import com.smartexpense.smart_expense_tracker.dto.response.statistic.CategoryExpenseOverTimeResponse;
import com.smartexpense.smart_expense_tracker.dto.response.statistic.CategoryExpenseSummaryResponse;
import com.smartexpense.smart_expense_tracker.dto.response.statistic.UserExpenseOverTimeResponse;
import com.smartexpense.smart_expense_tracker.dto.response.statistic.UserExpenseSummaryResponse;
import com.smartexpense.smart_expense_tracker.service.IStatisticService;

@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    private IStatisticService statisticService;

    @GetMapping("/user-expense-summary")
    public ApiResponse<List<UserExpenseSummaryResponse>> getUserExpenseSummary(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {

        LocalDate startDate = null;
        LocalDate endDate = null;

        // Parse values of startDate and endDate if not null or empty
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = LocalDate.parse(startDateStr);
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = LocalDate.parse(endDateStr);
        }

        List<UserExpenseSummaryResponse> userExpenseSummaryResponseList =
                statisticService.getUserExpenseSummary(startDate, endDate);

        ApiResponse<List<UserExpenseSummaryResponse>> results = new ApiResponse<>();
        results.setResult(userExpenseSummaryResponseList);

        return results;
    }

    @GetMapping("/user-expense-over-time")
    public ApiResponse<List<UserExpenseOverTimeResponse>> getUserExpenseOverTime(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {

        LocalDate startDate = null;
        LocalDate endDate = null;

        // Parse values of startDate and endDate if not null or empty
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = LocalDate.parse(startDateStr);
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = LocalDate.parse(endDateStr);
        }

        List<UserExpenseOverTimeResponse> userExpenseOverTimeResponseList =
                statisticService.getUserExpenseOverTime(startDate, endDate);

        ApiResponse<List<UserExpenseOverTimeResponse>> results = new ApiResponse<>();
        results.setResult(userExpenseOverTimeResponseList);

        return results;
    }

    @GetMapping("/category-expense-summary")
    public ApiResponse<List<CategoryExpenseSummaryResponse>> getCategoryExpenseSummary(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {

        LocalDate startDate = null;
        LocalDate endDate = null;

        // Parse values of startDate and endDate if not null or empty
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = LocalDate.parse(startDateStr);
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = LocalDate.parse(endDateStr);
        }

        List<CategoryExpenseSummaryResponse> categoryExpenseSummaryResponseList =
                statisticService.getCategoryExpenseSummary(startDate, endDate);

        ApiResponse<List<CategoryExpenseSummaryResponse>> results = new ApiResponse<>();
        results.setResult(categoryExpenseSummaryResponseList);

        return results;
    }

    @GetMapping("/category-expense-over-time")
    public ApiResponse<List<CategoryExpenseOverTimeResponse>> getCategoryExpenseOverTime(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {

        LocalDate startDate = null;
        LocalDate endDate = null;

        // Parse values of startDate and endDate if not null or empty
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = LocalDate.parse(startDateStr);
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = LocalDate.parse(endDateStr);
        }

        List<CategoryExpenseOverTimeResponse> categoryExpenseOverTimeResponseList =
                statisticService.getCategoryExpenseOverTime(startDate, endDate);

        ApiResponse<List<CategoryExpenseOverTimeResponse>> results = new ApiResponse<>();
        results.setResult(categoryExpenseOverTimeResponseList);

        return results;
    }

    @GetMapping("/category-expense-summary-user/{username}")
    public ApiResponse<List<CategoryExpenseSummaryResponse>> getCategoryExpenseSummaryByUser(
            @PathVariable("username") String username,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {

        LocalDate startDate = null;
        LocalDate endDate = null;

        // Parse values of startDate and endDate if not null or empty
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = LocalDate.parse(startDateStr);
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = LocalDate.parse(endDateStr);
        }

        List<CategoryExpenseSummaryResponse> categoryExpenseSummaryResponseList =
                statisticService.getCategoryExpenseSummaryByUser(username, startDate, endDate);

        ApiResponse<List<CategoryExpenseSummaryResponse>> results = new ApiResponse<>();
        results.setResult(categoryExpenseSummaryResponseList);

        return results;
    }

    @GetMapping("/category-expense-over-time-user/{username}")
    public ApiResponse<List<CategoryExpenseOverTimeResponse>> getCategoryExpenseOverTimeByUser(
            @PathVariable("username") String username,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {

        LocalDate startDate = null;
        LocalDate endDate = null;

        // Parse values of startDate and endDate if not null or empty
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = LocalDate.parse(startDateStr);
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = LocalDate.parse(endDateStr);
        }

        List<CategoryExpenseOverTimeResponse> categoryExpenseOverTimeResponseList =
                statisticService.getCategoryExpenseOverTimeByUser(username, startDate, endDate);

        ApiResponse<List<CategoryExpenseOverTimeResponse>> results = new ApiResponse<>();
        results.setResult(categoryExpenseOverTimeResponseList);

        return results;
    }
}
