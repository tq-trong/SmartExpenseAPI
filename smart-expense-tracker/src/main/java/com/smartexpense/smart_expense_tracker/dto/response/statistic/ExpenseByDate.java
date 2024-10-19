package com.smartexpense.smart_expense_tracker.dto.response.statistic;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseByDate {
    private LocalDate date;
    private BigDecimal amount;

    public ExpenseByDate(LocalDate date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
