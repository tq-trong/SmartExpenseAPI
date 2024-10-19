package com.smartexpense.smart_expense_tracker.dto.response.statistic;

import java.util.List;

public class UserExpenseOverTimeResponse {
    private String username;
    private List<ExpenseByDate> expenses;

    public UserExpenseOverTimeResponse(String username, List<ExpenseByDate> expenses) {
        this.username = username;
        this.expenses = expenses;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ExpenseByDate> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseByDate> expenses) {
        this.expenses = expenses;
    }
}
