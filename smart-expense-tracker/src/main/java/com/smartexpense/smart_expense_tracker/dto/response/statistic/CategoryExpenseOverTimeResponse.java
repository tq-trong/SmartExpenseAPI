package com.smartexpense.smart_expense_tracker.dto.response.statistic;

import java.util.List;

public class CategoryExpenseOverTimeResponse {
    private String category;
    private List<ExpenseByDate> expenses;

    public CategoryExpenseOverTimeResponse(String category, List<ExpenseByDate> expenses) {
        this.category = category;
        this.expenses = expenses;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ExpenseByDate> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseByDate> expenses) {
        this.expenses = expenses;
    }
}
