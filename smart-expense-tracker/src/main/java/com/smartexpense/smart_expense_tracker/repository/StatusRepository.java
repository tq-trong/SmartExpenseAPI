package com.smartexpense.smart_expense_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartexpense.smart_expense_tracker.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, String> {
    Optional<Status> findByStatusCode(String name);
}
