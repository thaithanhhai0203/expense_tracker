package com.learn.expense_tracker.dashboard.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.learn.expense_tracker.transaction.response.TransactionResponse;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DashboardResponse extends TransactionResponse {
    private String categoryName;
}
