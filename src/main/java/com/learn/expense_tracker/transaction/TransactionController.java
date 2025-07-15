package com.learn.expense_tracker.transaction;

import com.learn.expense_tracker.common.annotation.CurrentUser;
import com.learn.expense_tracker.common.annotation.RequireRoles;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.transaction.request.TransactionRequest;
import com.learn.expense_tracker.transaction.response.TransactionResponse;
import com.learn.expense_tracker.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Transaction", description = "Info Transaction")
@RestController
@RequestMapping("/api/transactions")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {
  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @Operation(summary = "Get list of transactions")
  @RequireRoles({"ADMIN", "USER"})
  @GetMapping
  public List<TransactionResponse> getAll(
      @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
      @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
      @CurrentUser User user) {
    LocalDateTime fromDateTime = from.atStartOfDay();
    LocalDateTime toDateTime = to.atTime(LocalTime.MAX);
    return this.transactionService.getAll(fromDateTime, toDateTime, user.getId());
  }

  @Operation(summary = "Get transaction detail")
  @RequireRoles({"ADMIN", "USER"})
  @GetMapping("/{id}")
  public TransactionResponse getById(@PathVariable Long id, @CurrentUser User user) {
    return this.transactionService.getById(id, user.getId());
  }

  @Operation(summary = "Create transaction")
  @RequireRoles({"ADMIN", "USER"})
  @PostMapping
  public SuccessCode create(
      @RequestBody TransactionRequest transactionRequest, @CurrentUser User user) {
    return this.transactionService.save(transactionRequest, user.getId());
  }

  @Operation(summary = "Update transaction")
  @RequireRoles({"ADMIN", "USER"})
  @PutMapping("/{id}")
  public SuccessCode update(
      @PathVariable Long id,
      @RequestBody TransactionRequest transactionRequest,
      @CurrentUser User user) {
    return this.transactionService.update(id, transactionRequest, user.getId());
  }

  @Operation(summary = "Delete transaction")
  @RequireRoles({"ADMIN", "USER"})
  @DeleteMapping("/{id}")
  public SuccessCode delete(@PathVariable Long id, @CurrentUser User user) {
    return this.transactionService.delete(id, user.getId());
  }
}
