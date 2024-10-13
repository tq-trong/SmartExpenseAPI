package com.smartexpense.smart_expense_tracker.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartexpense.smart_expense_tracker.entity.Invitation;
import com.smartexpense.smart_expense_tracker.entity.User;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, String> {
    Optional<Invitation> findByInviterAndInvitee(User inviter, User invitee);

    Page<Invitation> findByInvitee(User invitee, Pageable pageable);

    @Query("SELECT COUNT(i) FROM Invitation i WHERE i.invitee.username = :username OR i.inviter.username = :username")
    int countByInviteeOrInviter(@Param("username") String username);

    @Query(
            "SELECT i FROM Invitation i WHERE i.invitee = :invitee OR i.inviter = :invitee AND i.inviter.username LIKE %:username% OR i.invitee.username LIKE %:username%")
    Page<Invitation> findByInviteeAndInviterUsername(
            @Param("invitee") User invitee, @Param("username") String username, Pageable pageable);
}
