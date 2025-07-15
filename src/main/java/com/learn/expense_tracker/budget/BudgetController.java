package com.learn.expense_tracker.budget;

import com.learn.expense_tracker.budget.request.BudgetRequest;
import com.learn.expense_tracker.budget.response.BudgetResponse;
import com.learn.expense_tracker.common.annotation.CurrentUser;
import com.learn.expense_tracker.common.annotation.RequireRoles;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.user.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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
  public List<BudgetResponse> getAll(@Parameter(hidden = true) @CurrentUser User user) {
    return this.budgetService.getAll(user.getId());
  }

  @Operation(summary = "Get a list of budgets by week, month, year")
  @RequireRoles({"ADMIN", "USER"})
  @GetMapping("/")
  public List<BudgetResponse> getAll(@RequestParam int type, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @Parameter(hidden = true) @CurrentUser User user) {
    return this.budgetService.getAll(type, date, user.getId());
  }


  @Operation(summary = "Get budget detail")
  @RequireRoles({"ADMIN", "USER"})
  @GetMapping("/{id}")
  public BudgetResponse getById(@PathVariable Long id, @Parameter(hidden = true) @CurrentUser User user) {
    return this.budgetService.getById(id, user.getId());
  }

  @Operation(summary = "Create budget")
  @RequireRoles({"ADMIN", "USER"})
  @PostMapping
  public SuccessCode create(
      @RequestBody BudgetRequest budgetRequest, @Parameter(hidden = true) @CurrentUser User user) {
    return this.budgetService.save(budgetRequest, user.getId());
  }

  @Operation(summary = "Update budget")
  @RequireRoles({"ADMIN", "USER"})
  @PutMapping("/{id}")
  public SuccessCode update(
      @PathVariable Long id,
      @RequestBody BudgetRequest budgetRequest,
      @Parameter(hidden = true) @CurrentUser User user) {
    return this.budgetService.update(id, budgetRequest, user.getId());
  }

  @Operation(summary = "Delete budget")
  @RequireRoles({"ADMIN", "USER"})
  @DeleteMapping("/{id}")
  public SuccessCode delete(
      @PathVariable Long id, @Parameter(hidden = true) @CurrentUser User user) {
    return this.budgetService.delete(id, user.getId());
  }
}
