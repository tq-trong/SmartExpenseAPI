package com.smartexpense.smart_expense_tracker.repository;

import com.smartexpense.smart_expense_tracker.entity.Invitation;
import com.smartexpense.smart_expense_tracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InvitationRepository extends JpaRepository<Invitation, String> {
    Optional<Invitation> findByInviterAndInvitee(User inviter, User invitee);

    Page<Invitation> findByInvitee(User invitee, Pageable pageable);

    int countByInvitee(User user);

    @Query("SELECT i FROM Invitation i WHERE i.invitee = :invitee AND i.inviter.username LIKE %:inviterUsername%")
    Page<Invitation> findByInviteeAndInviterUsername(@Param("invitee") User invitee, @Param("inviterUsername") String inviterUsername, Pageable pageable);
}