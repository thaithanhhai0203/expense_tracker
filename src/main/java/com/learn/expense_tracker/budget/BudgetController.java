package com.learn.expense_tracker.budget;

import com.learn.expense_tracker.budget.request.BudgetRequest;
import com.learn.expense_tracker.budget.response.BudgetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Budget", description = "Info Budget")
@RestController
@RequestMapping("/api/budgets")
@SecurityRequirement(name = "bearerAuth")
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Operation(summary = "Get list of budgets")
    @GetMapping
    public List<BudgetResponse> getAll() {
        return this.budgetService.getAll();
    }

    @Operation(summary = "Get budget detail")
    @GetMapping("/{id}")
    public BudgetResponse getById(@PathVariable Long id) {
        return this.budgetService.getById(id);
    }

    @Operation(summary = "Create budget")
    @PostMapping
    public String create(@RequestBody BudgetRequest budgetRequest,  @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return this.budgetService.save(budgetRequest, token);
    }

    @Operation(summary = "Update budget")
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody BudgetRequest budgetRequest, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return this.budgetService.update(id, budgetRequest, token);
    }

    @Operation(summary = "Delete budget")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return this.budgetService.delete(id, token);
    }
}
