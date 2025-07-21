package com.learn.expense_tracker.budget.mapper;

import com.learn.expense_tracker.budget.Budget;
import com.learn.expense_tracker.budget.request.BudgetRequest;
import com.learn.expense_tracker.budget.response.BudgetResponse;
import com.learn.expense_tracker.category.Category;
import com.learn.expense_tracker.user.User;

public class BudgetMapper {
  public static Budget toEntity(BudgetRequest budgetRequest, Category category, User user) {
    Budget budget = new Budget();
    budget.setAmountLimit(budgetRequest.getAmountLimit());
    budget.setType(budgetRequest.getType());
    budget.setCategory(category);
    budget.setUser(user);
    return budget;
  }

  public static BudgetResponse toResponse(Budget budget) {
    BudgetResponse response = new BudgetResponse();
    response.setId(budget.getId());
    response.setAmountLimit(budget.getAmountLimit());
    response.setType(budget.getType());
    response.setCreatedAt(budget.getCreatedAt());
    response.setUpdatedAt(budget.getUpdatedAt());
    return response;
  }
}
