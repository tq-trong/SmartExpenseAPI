package com.smartexpense.smart_expense_tracker.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.smartexpense.smart_expense_tracker.dto.ExpenseDTO;

public interface IExpenseService extends IBaseService<ExpenseDTO> {
    List<ExpenseDTO> getExpenses(
            String userFilter,
            LocalDate startDate,
            LocalDate endDate,
            String category,
            String search,
            Pageable pageable);

    ExpenseDTO updateExpense(String expenseId, ExpenseDTO dto);

    List<String> getAllCategoryByFamily();

    void deleteExpense(String expenseId);

    long totalExpenses(
            String userFilter,
            LocalDate startDate,
            LocalDate endDate,
            String category,
            String search,
            Pageable pageable);
}
