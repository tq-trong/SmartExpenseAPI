package com.smartexpense.smart_expense_tracker.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartexpense.smart_expense_tracker.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Set<User> findAllByIdIn(Set<String> ids);

    @Query(
            "SELECT u FROM Family f JOIN f.user u WHERE f.id = :familyId AND u.username <> :username AND u.username LIKE %:search%")
    Page<User> findUsersExceptOne(
            @Param("search") String search,
            @Param("familyId") String familyId,
            @Param("username") String username,
            Pageable pageable);

    @Query("SELECT u.username FROM Family f JOIN f.user u WHERE f.id = :familyId")
    Set<String> findAllUserByFamily(@Param("familyId") String familyId);
}
