package com.smartexpense.smart_expense_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error!", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "User existed!", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002, "User not existed!", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1003, "Username must be at least 4 characters!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least 6 characters!", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1005, "Username or Password is wrong!", HttpStatus.UNAUTHORIZED),
    DATE_INVALID(1006, "Date is invalid!", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    ROLE_NOT_FOUND(1008, "Role is invalid", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(1009, "Permission is invalid", HttpStatus.BAD_REQUEST),
    ALREADY_INVITED(1010, "You has already invited this user!", HttpStatus.BAD_REQUEST),
    STATUS_NOT_FOUND(1011, "Status is invalid", HttpStatus.BAD_REQUEST)
    ;
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
