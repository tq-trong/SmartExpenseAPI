package com.smartexpense.smart_expense_tracker.service;

import com.smartexpense.smart_expense_tracker.dto.ExpenseDTO;
import com.smartexpense.smart_expense_tracker.dto.UserDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IExpenseService extends IBaseService<ExpenseDTO>{
    List<ExpenseDTO> getExpenses (String userFilter, LocalDate startDate, LocalDate endDate, String category, String search, Pageable pageable);
    ExpenseDTO updateExpense(String expenseId, ExpenseDTO dto);
    void deleteExpense(String expenseId);
    long totalExpenses(String userFilter, LocalDate startDate, LocalDate endDate, String category, String search, Pageable pageable);
}
