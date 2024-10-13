package com.smartexpense.smart_expense_tracker.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartexpense.smart_expense_tracker.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
    @Query("SELECT e FROM Expense e " + "JOIN e.user u "
            + "JOIN Family f ON u MEMBER OF f.user "
            + "WHERE f.id = :familyId "
            + "AND (:username IS NULL OR u.username = :username) "
            + "AND (:startDate IS NULL OR e.expenseDate >= :startDate) "
            + "AND (:endDate IS NULL OR e.expenseDate <= :endDate) "
            + "AND (:category IS NULL OR e.category LIKE CONCAT('%', :category, '%')) "
            + "AND (:search IS NULL OR e.description LIKE CONCAT('%', :search, '%'))")
    Page<Expense> getExpense(
            @Param("familyId") String familyId,
            @Param("username") String username,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("category") String category,
            @Param("search") String search,
            Pageable pageable);
}
