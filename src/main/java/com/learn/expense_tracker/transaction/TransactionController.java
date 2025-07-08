package com.learn.expense_tracker.transaction;

import com.learn.expense_tracker.transaction.request.TransactionRequest;
import com.learn.expense_tracker.transaction.response.TransactionResponse;
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
  @GetMapping
  public List<TransactionResponse> getAll(
      @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
      @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
    LocalDateTime fromDateTime = from.atStartOfDay();
    LocalDateTime toDateTime = to.atTime(LocalTime.MAX);
    return this.transactionService.getAll(fromDateTime, toDateTime);
  }

  @Operation(summary = "Get transaction detail")
  @GetMapping("/{id}")
  public TransactionResponse getById(@PathVariable Long id) {
    return this.transactionService.getById(id);
  }

  @Operation(summary = "Create transaction")
  @PostMapping
  public String create(
      @RequestBody TransactionRequest transactionRequest,
      @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    return this.transactionService.save(transactionRequest, token);
  }

  @Operation(summary = "Update transaction")
  @PutMapping("/{id}")
  public String update(
      @PathVariable Long id,
      @RequestBody TransactionRequest transactionRequest,
      @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    return this.transactionService.update(id, transactionRequest, token);
  }

  @Operation(summary = "Delete transaction")
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    return this.transactionService.delete(id, token);
  }
}
