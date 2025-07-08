package com.learn.expense_tracker.common.exception;

import org.springframework.http.HttpStatus;

import com.learn.expense_tracker.common.dto.ErrorCode;

public class ApiException extends RuntimeException {
  private final HttpStatus status;

  public ApiException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public ApiException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.status = errorCode.getHttpStatus();
  }

  public HttpStatus getStatus() {
    return status;
  }
}
