package com.learn.expense_tracker.dashboard.mapper;

import com.learn.expense_tracker.dashboard.response.DashboardResponse;
import com.learn.expense_tracker.transaction.Transaction;

public class DashboardMapper {
    public static DashboardResponse toResponse(Transaction transaction) {
        DashboardResponse response = new DashboardResponse();
        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getType());
        response.setNote(transaction.getNote());
        response.setCreatedAt(transaction.getCreatedAt());
        response.setCategoryName(transaction.getCategory().getName());
        return response;
    }
}
