package com.smartexpense.smart_expense_tracker.dto;

public class InvitationDTO extends BaseDTO<InvitationDTO> {
    private UserDTO inviter;
    private String invitee;
    private StatusDTO status;

    public UserDTO getInviter() {
        return inviter;
    }

    public void setInviter(UserDTO inviter) {
        this.inviter = inviter;
    }

    public String getInvitee() {
        return invitee;
    }

    public void setInvitee(String invitee) {
        this.invitee = invitee;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }
}
