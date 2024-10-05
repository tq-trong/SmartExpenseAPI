package com.smartexpense.smart_expense_tracker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDTO {
    private String id;
    private UserDTO user;
    private String description;
    private BigDecimal amount;
    private String category;
    private LocalDateTime expenseDate;

    private int page = 0;
    private int totalPage = 0;
    private int limitItem = 10;
    private List<ExpenseDTO> listResult = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDateTime expenseDate) {
        this.expenseDate = expenseDate;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int setTotalPage(long totalItems) {
        return (int) Math.ceil((double) totalItems / limitItem);
    }

    public int getLimitItem() {
        return limitItem;
    }

    public void setLimitItem(int limitItem) {
        this.limitItem = limitItem;
    }

    public List<ExpenseDTO> getListResult() {
        return listResult;
    }

    public void setListResult(List<ExpenseDTO> listResult) {
        this.listResult = listResult;
    }
}
