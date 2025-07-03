package com.learn.expense_tracker.common.exception;

public class ApiException extends RuntimeException {
  public ApiException(String message) {
    super(message);
  }
}
