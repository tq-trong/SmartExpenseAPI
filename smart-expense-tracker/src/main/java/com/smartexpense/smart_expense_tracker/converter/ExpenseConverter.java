package com.smartexpense.smart_expense_tracker.converter;

import com.smartexpense.smart_expense_tracker.dto.ExpenseDTO;
import com.smartexpense.smart_expense_tracker.entity.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpenseConverter {
    @Autowired
    private UserConverter userConverter;

    public Expense toEntity(ExpenseDTO dto) {
        Expense entity = new Expense();

        entity.setUser(userConverter.toEntity(dto.getUser()));
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setAmount(dto.getAmount());
        entity.setExpenseDate(dto.getExpenseDate());
        return entity;
    }

    public ExpenseDTO toDTO(Expense entity) {
        ExpenseDTO dto = new ExpenseDTO();
        if(entity.getId() != null) dto.setId(entity.getId());

        dto.setUser(userConverter.toDTO(entity.getUser()));
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategory());
        dto.setAmount(entity.getAmount());
        dto.setExpenseDate(entity.getExpenseDate());
        dto.setExpenseDate(entity.getExpenseDate());
        return dto;
    }

    public Expense toEntity(Expense entity, ExpenseDTO dto) {
        entity.setUser(userConverter.toEntity(dto.getUser()));
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setAmount(dto.getAmount());
        entity.setExpenseDate(dto.getExpenseDate());

        return entity;
    }
}
