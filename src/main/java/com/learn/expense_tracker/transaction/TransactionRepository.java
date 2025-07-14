package com.learn.expense_tracker.transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  Optional<Transaction> findByIdAndUserId(Long id, Long userId);

  @Query(
      "SELECT t FROM Transaction t WHERE t.createdAt BETWEEN :from AND :to AND t.user.id = :userId")
  List<Transaction> findAllByDateBetweenAndUserId(
      @Param("from") LocalDateTime from,
      @Param("to") LocalDateTime to,
      @Param("userId") Long userId);
}
