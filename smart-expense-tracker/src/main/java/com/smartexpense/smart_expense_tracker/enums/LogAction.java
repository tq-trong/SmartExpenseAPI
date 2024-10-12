package com.smartexpense.smart_expense_tracker.enums;

public enum LogAction {
    CREATED(" has created "),
    UPDATED(" has updated "),
    DELETED(" has deleted ");

    private final String action;

    LogAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
