package com.learn.expense_tracker.user.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  private Long id;
  private String username;
  private String email;
  private String avatar;
  private String role;
  private LocalDateTime createdAt;
}
