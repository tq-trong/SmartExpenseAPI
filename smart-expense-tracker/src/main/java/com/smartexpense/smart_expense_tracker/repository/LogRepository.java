package com.smartexpense.smart_expense_tracker.repository;

import com.smartexpense.smart_expense_tracker.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {
}
