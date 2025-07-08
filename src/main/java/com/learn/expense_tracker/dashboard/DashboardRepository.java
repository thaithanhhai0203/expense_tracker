package com.learn.expense_tracker.dashboard;

import com.learn.expense_tracker.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DashboardRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t LEFT JOIN t.category c WHERE t.createdAt BETWEEN :from AND :to")
    List<Transaction> findAllByDateBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
