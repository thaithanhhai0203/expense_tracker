package com.learn.expense_tracker.dashboard;

import com.learn.expense_tracker.common.annotation.RequireRoles;
import com.learn.expense_tracker.dashboard.response.DashboardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Dashboard", description = "Info Dashboard")
@RestController
@RequestMapping("/api/summary")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {
  private final DashboardService dashboardService;

  public DashboardController(DashboardService dashboardService) {
    this.dashboardService = dashboardService;
  }

  @Operation(summary = "Get dashboards")
  @RequireRoles({"ADMIN", "USER"})
  @GetMapping
  public List<DashboardResponse> getAll(@RequestParam("yearMonth") String yearMonth) {
    YearMonth ym = YearMonth.parse(yearMonth);
    LocalDateTime from = ym.atDay(1).atStartOfDay();
    LocalDateTime to = ym.atEndOfMonth().atTime(LocalTime.MAX);
    return this.dashboardService.getAll(from, to);
  }
}
