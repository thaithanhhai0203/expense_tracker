package com.learn.expense_tracker.reset_password;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {
  @Id private String token;

  private String email;

  private LocalDateTime expiryDate;
}
