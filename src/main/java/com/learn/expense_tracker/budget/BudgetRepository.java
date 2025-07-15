package com.learn.expense_tracker.budget;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
  Optional<Budget> findByIdAndUserId(Long id, Long userId);
  List<Budget> findByUserId(Long userId);
  @Query("SELECT b FROM Budget b WHERE b.type = :type AND b.createdAt BETWEEN :from AND :to AND b.user.id = :userId")
  List<Budget> findAll(
          @Param("type") int type, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("userId") Long userId);
}
