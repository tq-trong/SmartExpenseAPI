package com.smartexpense.smart_expense_tracker.repository;

import java.util.List;

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
    @Query("SELECT i FROM Invitation i WHERE i.inviter = :inviter AND i.invitee = :invitee")
    List<Invitation> findByInviterAndInvitee(User inviter, User invitee);

    @Query(
            "SELECT i FROM Invitation i WHERE (i.invitee = :invitee OR i.inviter = :invitee) AND (i.inviter.username LIKE %:username% OR i.invitee.username LIKE %:username%) "
                    + "ORDER BY i.createdDate DESC")
    Page<Invitation> findByInviteeAndInviterUsername(
            @Param("invitee") User invitee, @Param("username") String username, Pageable pageable);
}
