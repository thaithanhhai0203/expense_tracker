package com.learn.expense_tracker.budget;

import com.learn.expense_tracker.budget.mapper.BudgetMapper;
import com.learn.expense_tracker.budget.request.BudgetRequest;
import com.learn.expense_tracker.budget.response.BudgetResponse;
import com.learn.expense_tracker.category.Category;
import com.learn.expense_tracker.category.CategoryRepository;
import com.learn.expense_tracker.common.constants.AppConstants;
import com.learn.expense_tracker.common.dto.ErrorCode;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.common.exception.ApiException;
import com.learn.expense_tracker.user.User;
import com.learn.expense_tracker.user.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {
  private final BudgetRepository budgetRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  public BudgetService(
      BudgetRepository budgetRepository,
      CategoryRepository categoryRepository,
      UserRepository userRepository) {
    this.budgetRepository = budgetRepository;
    this.categoryRepository = categoryRepository;
    this.userRepository = userRepository;
  }

  public List<BudgetResponse> getAll(Long userId) {
    List<Budget> budgets = this.budgetRepository.findByUserId(userId);
    return budgets.stream().map(BudgetMapper::toResponse).collect(Collectors.toList());
  }

  public List<BudgetResponse> getAll(int type, LocalDate date, Long userId) {
    LocalDateTime from = date.atStartOfDay();
    LocalDateTime to = null;
    if (type == AppConstants.BUDGET_WEEKLY) {
      to = from.plusWeeks(1);
    }

    if (type == AppConstants.BUDGET_MONTHLY) {
      to = from.plusMonths(1);
    }

    if (type == AppConstants.BUDGET_YEARLY) {
      to = from.plusYears(1);
    }
    List<Budget> budgets = this.budgetRepository.findAll(type, from, to, userId);
    return budgets.stream().map(BudgetMapper::toResponse).collect(Collectors.toList());
  }

  public BudgetResponse getById(Long id, Long userId) {
    Budget existingBudget =
        this.budgetRepository
            .findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ApiException(ErrorCode.BUDGET_NOT_FOUND));
    return BudgetMapper.toResponse(existingBudget);
  }

  public SuccessCode save(BudgetRequest budgetRequest, Long userId) {
    User user =
        this.userRepository
            .findById(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    Category category =
        this.categoryRepository
            .findByIdAndUserId(budgetRequest.getCategoryId(), userId)
            .orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND));

    Budget budget = BudgetMapper.toEntity(budgetRequest, category, user);
    this.budgetRepository.save(budget);
    return SuccessCode.BUDGET_CREATED;
  }

  public SuccessCode update(Long id, BudgetRequest budgetRequest, Long userId) {
    Budget existingBudget =
        this.budgetRepository
            .findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ApiException(ErrorCode.BUDGET_NOT_FOUND));

    existingBudget.setAmountLimit(budgetRequest.getAmountLimit());
    existingBudget.setType(budgetRequest.getType());
    this.budgetRepository.save(existingBudget);
    return SuccessCode.BUDGET_UPDATED;
  }

  public SuccessCode delete(Long id, Long userId) {
    Budget existingBudget =
        this.budgetRepository
            .findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ApiException(ErrorCode.BUDGET_NOT_FOUND));
    this.budgetRepository.delete(existingBudget);
    return SuccessCode.BUDGET_DELETED;
  }
}
