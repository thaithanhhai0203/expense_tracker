package com.learn.expense_tracker.transaction.mapper;

import com.learn.expense_tracker.category.Category;
import com.learn.expense_tracker.transaction.Transaction;
import com.learn.expense_tracker.transaction.request.TransactionRequest;
import com.learn.expense_tracker.transaction.response.TransactionResponse;
import com.learn.expense_tracker.user.User;

public class TransactionMapper {
    public static Transaction toEntity(TransactionRequest transactionRequest, Category category, User user){
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setType(transactionRequest.getType());
        transaction.setNote(transactionRequest.getNote());
        transaction.setCategory(category);
        transaction.setUser(user);
        return transaction;
    }

    public static TransactionResponse toResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getType());
        response.setNote(transaction.getNote());
        response.setCreatedAt(transaction.getCreatedAt());
        return response;
    }
}
