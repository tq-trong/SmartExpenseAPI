package com.smartexpense.smart_expense_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartexpense.smart_expense_tracker.entity.Family;

@Repository
public interface FamilyRepository extends JpaRepository<Family, String> {

    @Query("SELECT f FROM Family f JOIN f.user u WHERE u.username = :username")
    Optional<Family> findByUser(@Param("username") String username);

    @Query("SELECT f FROM Family f JOIN f.user u WHERE u.username = :username AND f.id = :id")
    Optional<Family> findByUserAndFamily(@Param("username") String username, @Param("id") String id);
}
