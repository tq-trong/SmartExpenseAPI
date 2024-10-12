package com.smartexpense.smart_expense_tracker.repository;

import com.smartexpense.smart_expense_tracker.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {
    @Query("SELECT l FROM Log l " +
            "JOIN l.user u " +
            "JOIN Family f ON u MEMBER OF f.user " +
            "WHERE (f.id = :familyId OR :family IS NULL) " +
            "AND (:username IS NULL OR u.username = :username) " +
            "AND (:startDate IS NULL OR l.createdDate >= :startDate) " +
            "AND (:endDate IS NULL OR l.createdDate <= :endDate) " +
            "AND (:search IS NULL OR l.description LIKE CONCAT('%', :search, '%'))")
    Page<Log> getLogsAllMembers(@Param("familyId") String familyId,
                                @Param("username") String username,
                                @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                @Param("search") String search,
                                Pageable pageable);

    @Query("SELECT l FROM Log l " +
            "JOIN l.user u " +
            "WHERE u.username = :username " +
            "AND (:startDate IS NULL OR l.createdDate >= :startDate) " +
            "AND (:endDate IS NULL OR l.createdDate <= :endDate) " +
            "AND (:search IS NULL OR l.description LIKE CONCAT('%', :search, '%'))")
    Page<Log> getLogsWithoutFamily(@Param("search") String search,
                                   @Param("username") String username,
                                   @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                                   Pageable pageable);
}
