package com.learn.expense_tracker.dashboard;

import com.learn.expense_tracker.dashboard.mapper.DashboardMapper;
import com.learn.expense_tracker.dashboard.response.DashboardResponse;
import com.learn.expense_tracker.transaction.Transaction;
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

    public List<DashboardResponse> getAll(LocalDateTime from, LocalDateTime to){
        List<Transaction> transactions = this.dashboardRepository.findAllByDateBetween(from, to);
        return transactions.stream().map(DashboardMapper::toResponse).collect(Collectors.toList());
    }

   
}
