package com.smartexpense.smart_expense_tracker.repository;

import com.smartexpense.smart_expense_tracker.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, String> {
    Optional<Status> findByStatusCode(String name);
}
