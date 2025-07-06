package com.learn.expense_tracker.budget;

import com.learn.expense_tracker.budget.mapper.BudgetMapper;
import com.learn.expense_tracker.budget.request.BudgetRequest;
import com.learn.expense_tracker.budget.response.BudgetResponse;
import com.learn.expense_tracker.category.Category;
import com.learn.expense_tracker.category.CategoryRepository;
import com.learn.expense_tracker.common.exception.ApiException;
import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.user.User;
import com.learn.expense_tracker.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final JwtService jwtService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public BudgetService(BudgetRepository budgetRepository, JwtService jwtService, CategoryRepository categoryRepository, UserRepository userRepository){
        this.budgetRepository = budgetRepository;
        this.jwtService = jwtService;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<BudgetResponse> getAll() {
        List<Budget> budgets = this.budgetRepository.findAll();
        return budgets.stream()
                .map(BudgetMapper::toResponse)
                .collect(Collectors.toList());
    }

    public String save(BudgetRequest budgetRequest, String token) {
        Long userId = jwtService.extractUserId(token);
        Category category = this.categoryRepository.findById(budgetRequest.getCategoryId()).orElseThrow(()->new ApiException("Category not found"));

        User user = this.userRepository.findById(userId).orElseThrow(()->new ApiException("User not found"));
        Budget budget = BudgetMapper.toEntity(budgetRequest, category, user);
        this.budgetRepository.save(budget);
        return "Category saved";
    }
}
