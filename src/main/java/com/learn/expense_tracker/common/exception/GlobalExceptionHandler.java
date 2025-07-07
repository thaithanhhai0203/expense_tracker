package com.learn.expense_tracker.common.exception;

import com.learn.expense_tracker.common.dto.ApiErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiErrorResponse<Object>> handleApiException(ApiException ex) {
    return ResponseEntity.status(ex.getStatus())
        .body(ApiErrorResponse.error(ex.getMessage(), LocalDateTime.now()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse<Object>> handleException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiErrorResponse.error(ex.getMessage(), LocalDateTime.now()));
  }
}
