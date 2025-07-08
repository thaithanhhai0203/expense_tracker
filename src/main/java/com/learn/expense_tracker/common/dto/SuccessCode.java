package com.learn.expense_tracker.common.dto;

public enum SuccessCode {
    USER_REGISTERED("User registered successfully"),
    USER_CREATED("User created successfully"),
    CATEGORY_CREATED("Category created successfully"),
    CATEGORY_UPDATED("Category updated successfully"),
    CATEGORY_DELETED("Category deleted successfully"),
    BUDGET_CREATED("Budget created successfully"),
    BUDGET_UPDATED("Budget updated successfully"),
    BUDGET_DELETED("Budget deleted successfully");

    private final String message;
    SuccessCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
