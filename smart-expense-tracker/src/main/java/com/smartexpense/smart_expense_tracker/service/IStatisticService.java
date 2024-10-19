package com.smartexpense.smart_expense_tracker.service;

import java.time.LocalDate;
import java.util.List;

import com.smartexpense.smart_expense_tracker.dto.response.statistic.CategoryExpenseOverTimeResponse;
import com.smartexpense.smart_expense_tracker.dto.response.statistic.CategoryExpenseSummaryResponse;
import com.smartexpense.smart_expense_tracker.dto.response.statistic.UserExpenseOverTimeResponse;
import com.smartexpense.smart_expense_tracker.dto.response.statistic.UserExpenseSummaryResponse;

public interface IStatisticService {
    List<UserExpenseSummaryResponse> getUserExpenseSummary(LocalDate startDate, LocalDate endDate);

    List<UserExpenseOverTimeResponse> getUserExpenseOverTime(LocalDate startDate, LocalDate endDate);

    List<CategoryExpenseSummaryResponse> getCategoryExpenseSummary(LocalDate startDate, LocalDate endDate);

    List<CategoryExpenseOverTimeResponse> getCategoryExpenseOverTime(LocalDate startDate, LocalDate endDate);

    List<CategoryExpenseSummaryResponse> getCategoryExpenseSummaryByUser(
            String username, LocalDate startDate, LocalDate endDate);

    List<CategoryExpenseOverTimeResponse> getCategoryExpenseOverTimeByUser(
            String username, LocalDate startDate, LocalDate endDate);
}
