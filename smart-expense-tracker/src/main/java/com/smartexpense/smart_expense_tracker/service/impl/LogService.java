package com.smartexpense.smart_expense_tracker.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smartexpense.smart_expense_tracker.converter.LogConverter;
import com.smartexpense.smart_expense_tracker.converter.UserConverter;
import com.smartexpense.smart_expense_tracker.dto.LogDTO;
import com.smartexpense.smart_expense_tracker.entity.Family;
import com.smartexpense.smart_expense_tracker.entity.Log;
import com.smartexpense.smart_expense_tracker.entity.User;
import com.smartexpense.smart_expense_tracker.exception.AppException;
import com.smartexpense.smart_expense_tracker.exception.ErrorCode;
import com.smartexpense.smart_expense_tracker.repository.FamilyRepository;
import com.smartexpense.smart_expense_tracker.repository.LogRepository;
import com.smartexpense.smart_expense_tracker.repository.UserRepository;
import com.smartexpense.smart_expense_tracker.service.ILogService;
import com.smartexpense.smart_expense_tracker.service.IUserService;

@Service
public class LogService implements ILogService {
    @Autowired
    private LogConverter logConverter;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private IUserService userService;

    @Autowired
    private FamilyRepository familyRepository;

    @Override
    public List<LogDTO> getLogs(
            String userFilter, LocalDate startDate, LocalDate endDate, String search, Pageable pageable) {
        return getPageLog(userFilter, startDate, endDate, search, pageable).stream()
                .map(logConverter::toDTO)
                .collect(Collectors.toList());
    }

    public Page<Log> getPageLog(
            String userFilter, LocalDate startDate, LocalDate endDate, String search, Pageable pageable) {
        User user = userRepository
                .findByUsername(userConverter.toEntity(userService.getMyInfo()).getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Optional<Family> optionalFamily = familyRepository.findByUser(user.getUsername());

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atTime(23, 59, 59) : null;

        if (Objects.equals(userFilter, "all")) userFilter = null;

        Page<Log> pageLogs;
        if (optionalFamily.isPresent())
            pageLogs = logRepository.getLogsAllMembers(
                    optionalFamily.get().getId(), userFilter, startDateTime, endDateTime, search, pageable);
        else
            pageLogs = logRepository.getLogsWithoutFamily(
                    search, user.getUsername(), startDateTime, endDateTime, pageable);

        return pageLogs;
    }

    @Override
    public long totalLogs(String userFilter, LocalDate startDate, LocalDate endDate, String search, Pageable pageable) {
        return getPageLog(userFilter, startDate, endDate, search, pageable).getTotalElements();
    }
}
