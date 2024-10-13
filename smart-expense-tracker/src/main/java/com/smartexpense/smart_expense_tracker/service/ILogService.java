package com.smartexpense.smart_expense_tracker.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.smartexpense.smart_expense_tracker.dto.LogDTO;

public interface ILogService {
    List<LogDTO> getLogs(String userFilter, LocalDate startDate, LocalDate endDate, String search, Pageable pageable);

    long totalLogs(String userFilter, LocalDate startDate, LocalDate endDate, String search, Pageable pageable);
}
