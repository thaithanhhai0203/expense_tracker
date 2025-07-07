package com.learn.expense_tracker.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ApiErrorResponse<T> {
  private String status;
  private String message;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp;

  public ApiErrorResponse(String status, String message, LocalDateTime timestamp) {
    this.status = status;
    this.message = message;
    this.timestamp = timestamp;
  }

  public static <T> ApiErrorResponse<T> error(String message, LocalDateTime timestamp) {
    return new ApiErrorResponse<>("error", message, timestamp);
  }
}
