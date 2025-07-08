package com.learn.expense_tracker.dashboard;

import com.learn.expense_tracker.dashboard.response.DashboardResponse;
import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.transaction.response.TransactionResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;

    }

   
}
