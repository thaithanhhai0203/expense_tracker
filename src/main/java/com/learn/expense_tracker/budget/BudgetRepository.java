package com.learn.expense_tracker.budget;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
  Optional<Budget> findByIdAndUserId(Long id, Long userId);
}
