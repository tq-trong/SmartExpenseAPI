package com.smartexpense.smart_expense_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error!", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "User existed!", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002, "User not existed!", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 4 characters!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least 6 characters!", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1005, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    DATE_INVALID(1006, "Date is invalid!", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    ROLE_NOT_FOUND(1008, "Role is invalid", HttpStatus.BAD_REQUEST),
    PERMISSION_INVALID(1009, "You do not have permission!", HttpStatus.BAD_REQUEST),
    ALREADY_INVITED(1010, "You has already invited this user!", HttpStatus.BAD_REQUEST),
    STATUS_NOT_FOUND(1011, "Status is invalid", HttpStatus.BAD_REQUEST),
    INVITATION_NOT_EXISTED(1012, "This invitation is invalid", HttpStatus.BAD_REQUEST),
    FAMILY_NOT_EXISTED(1013, "Family not existed", HttpStatus.BAD_REQUEST),
    UPDATE_PENDING_INVITATION(1014, "Only invitations with status PENDING can be updated!", HttpStatus.BAD_REQUEST),
    UPDATE_ACCEPTED_INVITATION(1015, "You have already family!", HttpStatus.BAD_REQUEST),
    INVITER_CANNOT_INVITE(1016, "You cannot invite others", HttpStatus.BAD_REQUEST),
    EXPENSE_NOT_EXISTED(1017, "Expense not existed!", HttpStatus.BAD_REQUEST),
    INVITEE_HAS_ALREADY_FAMILY(1018, "This user has already family", HttpStatus.BAD_REQUEST),
    FAIL_LOGIN(1019, "Username or password is incorrect!", HttpStatus.BAD_REQUEST);
    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}
