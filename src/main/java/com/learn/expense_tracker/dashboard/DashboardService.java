package com.learn.expense_tracker.dashboard;

import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.transaction.response.TransactionResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DashboardService {
    private final DashboardRepository dashboardRepository;
    private final JwtService jwtService;

    public DashboardService(DashboardRepository dashboardRepository, JwtService jwtService) {
        this.dashboardRepository = dashboardRepository;
        this.jwtService = jwtService;
    }


}
