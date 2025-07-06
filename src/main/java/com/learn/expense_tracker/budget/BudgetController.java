package com.learn.expense_tracker.budget;

import com.learn.expense_tracker.budget.request.BudgetRequest;
import com.learn.expense_tracker.budget.response.BudgetResponse;
import com.learn.expense_tracker.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
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
    public List<BudgetResponse>getAll() {
        return this.budgetService.getAll();
    }

    @Operation(summary = "Create budget")
    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@RequestBody BudgetRequest budgetRequest,  @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(ApiResponse.success(this.budgetService.save(budgetRequest, token), "Created budget"));
    }
}
