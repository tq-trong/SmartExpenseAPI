package com.smartexpense.smart_expense_tracker.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartexpense.smart_expense_tracker.converter.UserConverter;
import com.smartexpense.smart_expense_tracker.dto.response.statistic.*;
import com.smartexpense.smart_expense_tracker.entity.Family;
import com.smartexpense.smart_expense_tracker.entity.User;
import com.smartexpense.smart_expense_tracker.exception.AppException;
import com.smartexpense.smart_expense_tracker.exception.ErrorCode;
import com.smartexpense.smart_expense_tracker.repository.ExpenseRepository;
import com.smartexpense.smart_expense_tracker.repository.FamilyRepository;
import com.smartexpense.smart_expense_tracker.repository.UserRepository;
import com.smartexpense.smart_expense_tracker.service.IStatisticService;
import com.smartexpense.smart_expense_tracker.service.IUserService;

@Service
public class StatisticService implements IStatisticService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private IUserService userService;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public LocalDateTime[] convertToLocalDateTime(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) startDate = LocalDate.MIN;
        if (endDate == null) endDate = LocalDate.now();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return new LocalDateTime[] {startDateTime, endDateTime};
    }

    @Override
    public List<UserExpenseSummaryResponse> getUserExpenseSummary(
            LocalDate startDate, LocalDate endDate) { // For pie chart
        LocalDateTime[] dateTimes = convertToLocalDateTime(startDate, endDate);
        LocalDateTime startDateTime = dateTimes[0];
        LocalDateTime endDateTime = dateTimes[1];

        User user = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Family family = familyRepository
                .findByUser(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.FAMILY_NOT_EXISTED));

        List<Object[]> results = expenseRepository.findUserExpenseSummary(family.getId(), startDateTime, endDateTime);
        return results.stream()
                .map(result -> new UserExpenseSummaryResponse(
                        (String) result[0], // username
                        (BigDecimal) result[1] // amount summary
                        ))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserExpenseOverTimeResponse> getUserExpenseOverTime(
            LocalDate startDate, LocalDate endDate) { // For Line Chart
        LocalDateTime[] dateTimes = convertToLocalDateTime(startDate, endDate);
        LocalDateTime startDateTime = dateTimes[0];
        LocalDateTime endDateTime = dateTimes[1];

        User user = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Family family = familyRepository
                .findByUser(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.FAMILY_NOT_EXISTED));

        List<Object[]> results = expenseRepository.findUserExpenseOverTime(family.getId(), startDateTime, endDateTime);

        Map<String, List<ExpenseByDate>> userExpensesMap = new HashMap<>();

        for (Object[] result : results) {
            String username = (String) result[0];
            LocalDateTime date = (LocalDateTime) result[1];
            BigDecimal amount = (BigDecimal) result[2];

            // Thêm dữ liệu vào danh sách của user tương ứng
            userExpensesMap
                    .computeIfAbsent(username, k -> new ArrayList<>())
                    .add(new ExpenseByDate(date.toLocalDate(), amount));
        }

        return userExpensesMap.entrySet().stream()
                .map(entry -> new UserExpenseOverTimeResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryExpenseSummaryResponse> getCategoryExpenseSummary(LocalDate startDate, LocalDate endDate) {
        LocalDateTime[] dateTimes = convertToLocalDateTime(startDate, endDate);
        LocalDateTime startDateTime = dateTimes[0];
        LocalDateTime endDateTime = dateTimes[1];

        User user = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Family family = familyRepository
                .findByUser(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.FAMILY_NOT_EXISTED));

        List<Object[]> results =
                expenseRepository.findCategoryExpenseSummary(family.getId(), startDateTime, endDateTime);
        return results.stream()
                .map(result -> new CategoryExpenseSummaryResponse(
                        (String) result[0], // category
                        (BigDecimal) result[1] // amount summary
                        ))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryExpenseOverTimeResponse> getCategoryExpenseOverTime(LocalDate startDate, LocalDate endDate) {
        LocalDateTime[] dateTimes = convertToLocalDateTime(startDate, endDate);
        LocalDateTime startDateTime = dateTimes[0];
        LocalDateTime endDateTime = dateTimes[1];

        User user = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Family family = familyRepository
                .findByUser(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.FAMILY_NOT_EXISTED));

        List<Object[]> results =
                expenseRepository.findCategoryExpenseOverTime(family.getId(), startDateTime, endDateTime);

        Map<String, List<ExpenseByDate>> categoryExpensesMap = new HashMap<>();

        for (Object[] result : results) {
            String category = (String) result[0];
            LocalDateTime date = (LocalDateTime) result[1];
            BigDecimal amount = (BigDecimal) result[2];

            categoryExpensesMap
                    .computeIfAbsent(category, k -> new ArrayList<>())
                    .add(new ExpenseByDate(date.toLocalDate(), amount));
        }

        return categoryExpensesMap.entrySet().stream()
                .map(entry -> new CategoryExpenseOverTimeResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryExpenseSummaryResponse> getCategoryExpenseSummaryByUser(
            String username, LocalDate startDate, LocalDate endDate) {
        LocalDateTime[] dateTimes = convertToLocalDateTime(startDate, endDate);
        LocalDateTime startDateTime = dateTimes[0];
        LocalDateTime endDateTime = dateTimes[1];

        User userLogin = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Optional<Family> optionalFamily = familyRepository.findByUser(userLogin.getUsername());

        List<Object[]> results;

        if (optionalFamily.isPresent()) {
            User userFilter = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            if (!optionalFamily.get().getUser().contains(userFilter))
                throw new AppException(ErrorCode.PERMISSION_INVALID);

            results = expenseRepository.findCategoryExpenseSummaryByUser(username, startDateTime, endDateTime);
        } else
            results = expenseRepository.findCategoryExpenseSummaryByUser(
                    userLogin.getUsername(), startDateTime, endDateTime);

        return results.stream()
                .map(result -> new CategoryExpenseSummaryResponse(
                        (String) result[0], // category
                        (BigDecimal) result[1] // amount summary
                        ))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryExpenseOverTimeResponse> getCategoryExpenseOverTimeByUser(
            String username, LocalDate startDate, LocalDate endDate) {
        LocalDateTime[] dateTimes = convertToLocalDateTime(startDate, endDate);
        LocalDateTime startDateTime = dateTimes[0];
        LocalDateTime endDateTime = dateTimes[1];

        User userLogin = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<Object[]> results;

        Optional<Family> optionalFamily = familyRepository.findByUser(userLogin.getUsername());

        if (optionalFamily.isPresent()) {
            User userFilter = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            if (!optionalFamily.get().getUser().contains(userFilter))
                throw new AppException(ErrorCode.PERMISSION_INVALID);

            results = expenseRepository.findCategoryExpenseOverTimeByUser(username, startDateTime, endDateTime);
        } else
            results = expenseRepository.findCategoryExpenseOverTimeByUser(
                    userLogin.getUsername(), startDateTime, endDateTime);

        Map<String, List<ExpenseByDate>> categoryExpensesMap = new HashMap<>();

        for (Object[] result : results) {
            String category = (String) result[0];
            LocalDateTime date = (LocalDateTime) result[1];
            BigDecimal amount = (BigDecimal) result[2];

            categoryExpensesMap
                    .computeIfAbsent(category, k -> new ArrayList<>())
                    .add(new ExpenseByDate(date.toLocalDate(), amount));
        }

        return categoryExpensesMap.entrySet().stream()
                .map(entry -> new CategoryExpenseOverTimeResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
