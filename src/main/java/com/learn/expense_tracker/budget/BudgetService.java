package com.learn.expense_tracker.budget;

import com.learn.expense_tracker.budget.mapper.BudgetMapper;
import com.learn.expense_tracker.budget.request.BudgetRequest;
import com.learn.expense_tracker.budget.response.BudgetResponse;
import com.learn.expense_tracker.category.Category;
import com.learn.expense_tracker.category.CategoryRepository;
import com.learn.expense_tracker.common.dto.ErrorCode;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.common.exception.ApiException;
import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.user.User;
import com.learn.expense_tracker.user.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {
  private final BudgetRepository budgetRepository;
  private final JwtService jwtService;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  public BudgetService(
      BudgetRepository budgetRepository,
      JwtService jwtService,
      CategoryRepository categoryRepository,
      UserRepository userRepository) {
    this.budgetRepository = budgetRepository;
    this.jwtService = jwtService;
    this.categoryRepository = categoryRepository;
    this.userRepository = userRepository;
  }

  public List<BudgetResponse> getAll() {
    List<Budget> budgets = this.budgetRepository.findAll();
    return budgets.stream().map(BudgetMapper::toResponse).collect(Collectors.toList());
  }

  public BudgetResponse getById(Long id) {
    Budget existingBudget =
        this.budgetRepository
            .findById(id)
            .orElseThrow(() -> new ApiException(ErrorCode.BUDGET_NOT_FOUND));
    return BudgetMapper.toResponse(existingBudget);
  }

  public SuccessCode save(BudgetRequest budgetRequest, String token) {
    Long userId = jwtService.extractUserId(token);
    Category category =
        this.categoryRepository
            .findById(budgetRequest.getCategoryId())
            .orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND));

    User user =
        this.userRepository
            .findById(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    Budget budget = BudgetMapper.toEntity(budgetRequest, category, user);

    this.budgetRepository.save(budget);
    return SuccessCode.BUDGET_CREATED;
  }

  public SuccessCode update(Long id, BudgetRequest budgetRequest, String token) {
    Long userId = jwtService.extractUserId(token);
    Budget existingBudget =
        this.budgetRepository
            .findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ApiException(ErrorCode.BUDGET_NOT_FOUND));

    existingBudget.setAmountLimit(budgetRequest.getAmountLimit());
    existingBudget.setMonth(budgetRequest.getMonth());
    this.budgetRepository.save(existingBudget);
    return SuccessCode.BUDGET_UPDATED;
  }

  public SuccessCode delete(Long id, String token) {
    Long userId = jwtService.extractUserId(token);
    Budget existingBudget =
        this.budgetRepository
            .findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ApiException(ErrorCode.BUDGET_NOT_FOUND));
    this.budgetRepository.delete(existingBudget);
    return SuccessCode.BUDGET_DELETED;
  }
}
