package com.learn.expense_tracker.transaction.response;


import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DashboardResponse extends TransactionResponse {
    private String name;
}
