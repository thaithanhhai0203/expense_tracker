package com.learn.expense_tracker.transaction;

import com.learn.expense_tracker.category.Category;
import com.learn.expense_tracker.category.CategoryRepository;
import com.learn.expense_tracker.common.dto.ErrorCode;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.common.exception.ApiException;
import com.learn.expense_tracker.transaction.mapper.TransactionMapper;
import com.learn.expense_tracker.transaction.request.TransactionRequest;
import com.learn.expense_tracker.transaction.response.TransactionResponse;
import com.learn.expense_tracker.user.User;
import com.learn.expense_tracker.user.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  public TransactionService(
      TransactionRepository transactionRepository,
      CategoryRepository categoryRepository,
      UserRepository userRepository) {
    this.transactionRepository = transactionRepository;
    this.categoryRepository = categoryRepository;
    this.userRepository = userRepository;
  }

  public List<TransactionResponse> getAll(LocalDateTime from, LocalDateTime to, Long userId) {
    List<Transaction> transactions =
        this.transactionRepository.findAllByDateBetweenAndUserId(from, to, userId);
    return transactions.stream().map(TransactionMapper::toResponse).collect(Collectors.toList());
  }

  public TransactionResponse getById(Long id, Long userId) {
    Transaction existingTransaction =
        this.transactionRepository
            .findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRANSACTION_NOT_FOUND));
    return TransactionMapper.toResponse(existingTransaction);
  }

  public SuccessCode save(TransactionRequest transactionRequest, Long userId) {
    Category category =
        this.categoryRepository
            .findById(transactionRequest.getCategoryId())
            .orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND));

    User user =
        this.userRepository
            .findById(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    Transaction transaction = TransactionMapper.toEntity(transactionRequest, category, user);
    this.transactionRepository.save(transaction);
    return SuccessCode.TRANSACTION_CREATED;
  }

  public SuccessCode update(Long id, TransactionRequest transactionRequest, Long userId) {
    Transaction foundTransaction =
        this.transactionRepository
            .findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRANSACTION_NOT_FOUND));

    foundTransaction.setAmount(transactionRequest.getAmount());
    foundTransaction.setType(transactionRequest.getType());
    foundTransaction.setNote(transactionRequest.getNote());
    this.transactionRepository.save(foundTransaction);
    return SuccessCode.TRANSACTION_UPDATED;
  }

  public SuccessCode delete(Long id, Long userId) {
    Transaction foundTransaction =
        this.transactionRepository
            .findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ApiException(ErrorCode.TRANSACTION_NOT_FOUND));
    this.transactionRepository.delete(foundTransaction);
    return SuccessCode.TRANSACTION_DELETED;
  }
}
