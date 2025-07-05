package com.learn.expense_tracker.common.exception;

import com.learn.expense_tracker.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiResponse<Object>> handleApiException(ApiException ex) {
    return ResponseEntity
            .badRequest()
            .body(ApiResponse.error(ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
    return ResponseEntity
            .internalServerError()
            .body(ApiResponse.error(ex.getMessage()));
  }
}
