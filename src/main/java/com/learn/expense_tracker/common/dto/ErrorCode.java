package com.learn.expense_tracker.common.dto;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
  INVALID_USERNAME_OR_PASSWORD("INVALID_USERNAME_OR_PASSWORD", HttpStatus.UNAUTHORIZED),
  USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", HttpStatus.BAD_REQUEST),
  USER_NOT_FOUND("User not found", HttpStatus.BAD_REQUEST),
  CATEGORY_NOT_FOUND("Category not found", HttpStatus.BAD_REQUEST),
  CATEGORY_ALREADY_EXISTS("Category already exists", HttpStatus.BAD_REQUEST),
  BUDGET_NOT_FOUND("Budget not found", HttpStatus.BAD_REQUEST);

  private final String message;
  private final HttpStatus httpStatus;

  ErrorCode(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
