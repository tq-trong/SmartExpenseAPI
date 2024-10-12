package com.smartexpense.smart_expense_tracker.service;

import com.smartexpense.smart_expense_tracker.dto.LogDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ILogService {
    List<LogDTO> getLogs(String userFilter, LocalDate startDate, LocalDate endDate, String search, Pageable pageable);
    long totalLogs(String userFilter, LocalDate startDate, LocalDate endDate, String search, Pageable pageable);
}
