package com.learn.expense_tracker.budget;

import com.learn.expense_tracker.budget.request.BudgetRequest;
import com.learn.expense_tracker.budget.response.BudgetResponse;
import com.learn.expense_tracker.common.annotation.CurrentUser;
import com.learn.expense_tracker.common.annotation.RequireRoles;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Budget", description = "Info Budget")
@RestController
@RequestMapping("/api/budgets")
@SecurityRequirement(name = "bearerAuth")
public class BudgetController {
  private final BudgetService budgetService;

  public BudgetController(BudgetService budgetService) {
    this.budgetService = budgetService;
  }

  @Operation(summary = "Get list of budgets")
  @RequireRoles({"ADMIN", "USER"})
  @GetMapping
  public List<BudgetResponse> getAll(@CurrentUser User user) {
    return this.budgetService.getAll(user.getId());
  }

  @Operation(summary = "Get budget detail")
  @RequireRoles({"ADMIN", "USER"})
  @GetMapping("/{id}")
  public BudgetResponse getById(@PathVariable Long id, @CurrentUser User user) {
    return this.budgetService.getById(id, user.getId());
  }

  @Operation(summary = "Create budget")
  @RequireRoles({"ADMIN", "USER"})
  @PostMapping
  public SuccessCode create(@RequestBody BudgetRequest budgetRequest, @CurrentUser User user) {
    return this.budgetService.save(budgetRequest, user.getId());
  }

  @Operation(summary = "Update budget")
  @RequireRoles({"ADMIN", "USER"})
  @PutMapping("/{id}")
  public SuccessCode update(
      @PathVariable Long id, @RequestBody BudgetRequest budgetRequest, @CurrentUser User user) {
    return this.budgetService.update(id, budgetRequest, user.getId());
  }

  @Operation(summary = "Delete budget")
  @RequireRoles({"ADMIN", "USER"})
  @DeleteMapping("/{id}")
  public SuccessCode delete(@PathVariable Long id, @CurrentUser User user) {
    return this.budgetService.delete(id, user.getId());
  }
}
