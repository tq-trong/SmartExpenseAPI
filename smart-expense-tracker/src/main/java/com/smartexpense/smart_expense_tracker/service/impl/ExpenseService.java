package com.smartexpense.smart_expense_tracker.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smartexpense.smart_expense_tracker.converter.ExpenseConverter;
import com.smartexpense.smart_expense_tracker.converter.UserConverter;
import com.smartexpense.smart_expense_tracker.dto.ExpenseDTO;
import com.smartexpense.smart_expense_tracker.entity.Expense;
import com.smartexpense.smart_expense_tracker.entity.Family;
import com.smartexpense.smart_expense_tracker.entity.User;
import com.smartexpense.smart_expense_tracker.enums.LogAction;
import com.smartexpense.smart_expense_tracker.exception.AppException;
import com.smartexpense.smart_expense_tracker.exception.ErrorCode;
import com.smartexpense.smart_expense_tracker.repository.ExpenseRepository;
import com.smartexpense.smart_expense_tracker.repository.FamilyRepository;
import com.smartexpense.smart_expense_tracker.repository.UserRepository;
import com.smartexpense.smart_expense_tracker.service.IExpenseService;
import com.smartexpense.smart_expense_tracker.service.IUserService;

@Service
public class ExpenseService implements IExpenseService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private IUserService userService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseConverter expenseConverter;

    @Autowired
    private FamilyRepository familyRepository;

    @Override
    public List<ExpenseDTO> getExpenses(
            String userFilter,
            LocalDate startDate,
            LocalDate endDate,
            String category,
            String search,
            Pageable pageable) {

        return getPageExpense(userFilter, startDate, endDate, category, search, pageable).stream()
                .map(expenseConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllCategoryByFamily() {
        User user = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Optional<Family> optionalFamily = familyRepository.findByUser(user.getUsername());

        List<String> listCategory = new ArrayList<>();

        if (optionalFamily.isPresent())
            listCategory = expenseRepository.findAllCategoryByFamily(
                    optionalFamily.get().getId());
        else listCategory = expenseRepository.findAllCategoryByUser(user.getUsername());
        return listCategory;
    }

    @Override
    public ExpenseDTO updateExpense(String expenseId, ExpenseDTO dto) {
        User user = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Expense expense = expenseRepository
                .findById(expenseId)
                .orElseThrow(() -> new AppException(ErrorCode.EXPENSE_NOT_EXISTED));
        if (expense.getUser().equals(user)) {
            expense = expenseConverter.toEntity(expense, dto);

            expenseRepository.save(expense);
            userService.createLog(
                    user.getUsername(),
                    user.getUsername() + LogAction.UPDATED.getAction() + "expense " + expense.getId());
        } else throw new AppException(ErrorCode.PERMISSION_INVALID);

        return expenseConverter.toDTO(expense);
    }

    @Override
    public void deleteExpense(String expenseId) {
        User user = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Expense expense = expenseRepository
                .findById(expenseId)
                .orElseThrow(() -> new AppException(ErrorCode.EXPENSE_NOT_EXISTED));
        if (expense.getUser().equals(user)) {
            userService.createLog(
                    user.getUsername(),
                    user.getUsername() + LogAction.DELETED.getAction() + "expense " + expense.getId());
            expenseRepository.delete(expense);
        } else throw new AppException(ErrorCode.PERMISSION_INVALID);
    }

    @Override
    public long totalExpenses(
            String userFilter,
            LocalDate startDate,
            LocalDate endDate,
            String category,
            String search,
            Pageable pageable) {

        return getPageExpense(userFilter, startDate, endDate, category, search, pageable)
                .getTotalElements();
    }

    public Page<Expense> getPageExpense(
            String userFilter,
            LocalDate startDate,
            LocalDate endDate,
            String category,
            String search,
            Pageable pageable) {
        User user = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (Objects.equals(userFilter, "all")) userFilter = null;
        if (Objects.equals(category, "all")) category = null;

        Optional<Family> optionalFamily = familyRepository.findByUser(user.getUsername());

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atTime(23, 59, 59) : null;
        Page<Expense> expensesPage;
        if (optionalFamily.isPresent()) {
            expensesPage = expenseRepository.getExpense(
                    optionalFamily.get().getId(), userFilter, startDateTime, endDateTime, category, search, pageable);
        } else {
            expensesPage = expenseRepository.getExpenseWithoutFamily(
                    user.getUsername(), startDateTime, endDateTime, category, search, pageable);
        }
        return expensesPage;
    }

    @Override
    public ExpenseDTO create(ExpenseDTO dto) {
        User user = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Expense expense = expenseConverter.toEntity(dto);
        expense.setUser(user);

        expenseRepository.save(expense);
        userService.createLog(user.getUsername(), user.getUsername() + LogAction.CREATED.getAction() + "new expense");
        return expenseConverter.toDTO(expense);
    }

    @Override
    public ExpenseDTO get(String id) {
        Expense expense =
                expenseRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.EXPENSE_NOT_EXISTED));
        System.out.println(expense.getId() + expense.getDescription());
        return expenseConverter.toDTO(expense);
    }
}
