package com.learn.expense_tracker.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
  private String status;
  private String errorCode;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp;

  private T data;

  public ApiResponse(ErrorCode errorCode) {
    this.status = "error";
    this.errorCode = errorCode.getMessage();
    this.timestamp = LocalDateTime.now();
    this.data = null;
  }

  public ApiResponse(String message) {
    this.status = "error";
    this.errorCode = message;
    this.timestamp = LocalDateTime.now();
    this.data = null;
  }

  public ApiResponse(String message, T data) {
    this.status = "success";
    this.errorCode = message;
    this.timestamp = LocalDateTime.now();
    this.data = data;
  }

  public ApiResponse(String message, String status) {
    this.status = status;
    this.errorCode = message;
    this.timestamp = LocalDateTime.now();
    this.data = null;
  }

  public static <T> ApiResponse<T> error(ErrorCode errorCode) {
    return new ApiResponse<>(errorCode);
  }

  public static <T> ApiResponse<T> error(String message) {
    return new ApiResponse<>(message);
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(null, data);
  }
}
