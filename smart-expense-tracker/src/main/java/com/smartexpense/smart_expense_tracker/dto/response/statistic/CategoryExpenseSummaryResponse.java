package com.smartexpense.smart_expense_tracker.dto.response.statistic;

import java.math.BigDecimal;

public class CategoryExpenseSummaryResponse {
    private String category;
    private BigDecimal totalAmount;

    public CategoryExpenseSummaryResponse(String category, BigDecimal totalAmount) {
        this.category = category;
        this.totalAmount = totalAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
