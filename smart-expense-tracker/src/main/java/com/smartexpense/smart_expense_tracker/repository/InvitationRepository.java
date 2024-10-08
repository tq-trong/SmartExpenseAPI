package com.smartexpense.smart_expense_tracker.repository;

import com.smartexpense.smart_expense_tracker.entity.Invitation;
import com.smartexpense.smart_expense_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, String> {
    Optional<Invitation> findByInviterAndInvitee(User inviter, User invitee);
}
