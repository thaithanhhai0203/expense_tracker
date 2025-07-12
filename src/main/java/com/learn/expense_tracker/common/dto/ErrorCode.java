package com.learn.expense_tracker.common.dto;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
  INVALID_USERNAME_OR_PASSWORD("INVALID_USERNAME_OR_PASSWORD", HttpStatus.UNAUTHORIZED),
  USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", HttpStatus.BAD_REQUEST),
  USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.BAD_REQUEST),
  CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", HttpStatus.BAD_REQUEST),
  CATEGORY_ALREADY_EXISTS("CATEGORY_ALREADY_EXISTS", HttpStatus.BAD_REQUEST),
  BUDGET_NOT_FOUND("BUDGET_NOT_FOUND", HttpStatus.BAD_REQUEST),
  TRANSACTION_NOT_FOUND("TRANSACTION_NOT_FOUND", HttpStatus.BAD_REQUEST);

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
