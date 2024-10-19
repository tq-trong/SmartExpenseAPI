package com.smartexpense.smart_expense_tracker.dto.response.statistic;

import java.math.BigDecimal;

public class UserExpenseSummaryResponse {
    private String username;
    private BigDecimal totalAmount;

    public UserExpenseSummaryResponse(String username, BigDecimal totalAmount) {
        this.username = username;
        this.totalAmount = totalAmount;
    }

    public UserExpenseSummaryResponse() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
