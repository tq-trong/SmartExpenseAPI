package com.smartexpense.smart_expense_tracker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartexpense.smart_expense_tracker.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
    @Query("SELECT e.category FROM Expense e " + "JOIN Family f ON e.user MEMBER OF f.user "
            + "WHERE  f.id = :familyId "
            + "GROUP BY e.category")
    List<String> findAllCategoryByFamily(@Param("familyId") String familyId);

    @Query("SELECT DISTINCT(e.category) FROM Expense e " + "WHERE  e.user.username = :username ")
    List<String> findAllCategoryByUser(@Param("username") String username);

    @Query("SELECT e FROM Expense e " + "JOIN e.user u "
            + "JOIN Family f ON u MEMBER OF f.user "
            + "WHERE f.id = :familyId "
            + "AND (:username IS NULL OR u.username = :username) "
            + "AND (:startDate IS NULL OR e.expenseDate >= :startDate) "
            + "AND (:endDate IS NULL OR e.expenseDate <= :endDate) "
            + "AND (:category IS NULL OR e.category = :category) "
            + "AND (:search IS NULL OR e.description LIKE CONCAT('%', :search, '%')) "
            + "ORDER BY e.expenseDate DESC")
    Page<Expense> getExpense(
            @Param("familyId") String familyId,
            @Param("username") String username,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("category") String category,
            @Param("search") String search,
            Pageable pageable);

    @Query("SELECT e FROM Expense e " + "JOIN e.user u "
            + "WHERE u.username = :username "
            + "AND (:startDate IS NULL OR e.expenseDate >= :startDate) "
            + "AND (:endDate IS NULL OR e.expenseDate <= :endDate) "
            + "AND (:category IS NULL OR e.category = :category) "
            + "AND (:search IS NULL OR e.description LIKE CONCAT('%', :search, '%')) "
            + "ORDER BY e.expenseDate DESC")
    Page<Expense> getExpenseWithoutFamily(
            @Param("username") String username,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("category") String category,
            @Param("search") String search,
            Pageable pageable);

    @Query("SELECT e.user.username, SUM(e.amount) FROM Expense e " + "JOIN Family f ON e.user MEMBER OF f.user "
            + "WHERE  f.id = :familyId "
            + "AND e.expenseDate BETWEEN :startDate AND :endDate GROUP BY e.user.username")
    List<Object[]> findUserExpenseSummary(
            @Param("familyId") String familyId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT e.user.username, e.expenseDate, SUM(e.amount) FROM Expense e "
            + "JOIN Family f ON e.user MEMBER OF f.user "
            + "WHERE  f.id = :familyId "
            + "AND e.expenseDate BETWEEN :startDate AND :endDate GROUP BY e.user.username, e.expenseDate "
            + "ORDER BY e.expenseDate")
    List<Object[]> findUserExpenseOverTime(
            @Param("familyId") String familyId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT e.category, SUM(e.amount) FROM Expense e " + "JOIN Family f ON e.user MEMBER OF f.user "
            + "WHERE  f.id = :familyId "
            + "AND e.expenseDate BETWEEN :startDate AND :endDate GROUP BY e.category")
    List<Object[]> findCategoryExpenseSummary(
            @Param("familyId") String familyId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT e.category, e.expenseDate, SUM(e.amount) FROM Expense e "
            + "JOIN Family f ON e.user MEMBER OF f.user "
            + "WHERE  f.id = :familyId "
            + "AND e.expenseDate BETWEEN :startDate AND :endDate GROUP BY e.category, e.expenseDate "
            + "ORDER BY e.expenseDate")
    List<Object[]> findCategoryExpenseOverTime(
            @Param("familyId") String familyId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT e.category, SUM(e.amount) FROM Expense e " + "WHERE  e.user.username = :username "
            + "AND e.expenseDate BETWEEN :startDate AND :endDate GROUP BY e.category")
    List<Object[]> findCategoryExpenseSummaryByUser(
            @Param("username") String username,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT e.category, e.expenseDate, SUM(e.amount) FROM Expense e " + "WHERE  e.user.username = :username "
            + "AND e.expenseDate BETWEEN :startDate AND :endDate GROUP BY e.category, e.expenseDate "
            + "ORDER BY e.expenseDate")
    List<Object[]> findCategoryExpenseOverTimeByUser(
            @Param("username") String username,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
