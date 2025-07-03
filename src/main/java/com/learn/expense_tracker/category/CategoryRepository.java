package com.learn.expense_tracker.category;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  Optional<Category> findByNameAndUserId(String name, Long userId);

  Optional<Category> findByIdAndUserId(Long id, Long userId);
}
