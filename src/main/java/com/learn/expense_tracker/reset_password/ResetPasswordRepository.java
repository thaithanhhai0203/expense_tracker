package com.learn.expense_tracker.reset_password;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, String> {
  Optional<ResetPassword> findByToken(String token);
}
