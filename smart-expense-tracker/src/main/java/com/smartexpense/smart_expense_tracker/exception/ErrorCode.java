package com.smartexpense.smart_expense_tracker.exception;

public enum ErrorCode {
    USER_EXISTED(1001, "User existed!"),
    USER_NOT_EXISTED(1002, "User not existed!"),
    USERNAME_INVALID(1003, "Username must be at least 4 characters!"),
    PASSWORD_INVALID(1004, "Password must be at least 6 characters!"),
    UNAUTHENTICATED(1005, "Username or Password is wrong!")
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
