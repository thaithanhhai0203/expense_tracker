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

import org.springframework.http.HttpStatus;
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

    public BudgetResponse getById(Long id) {
        Budget existingBudget =
                this.budgetRepository
                        .findById(id)
                        .orElseThrow(() -> new ApiException("Budget not found", HttpStatus.BAD_REQUEST));
        return BudgetMapper.toResponse(existingBudget);
    }

    public String save(BudgetRequest budgetRequest, String token) {
        Long userId = jwtService.extractUserId(token);
        Category category = this.categoryRepository.findById(budgetRequest.getCategoryId()).orElseThrow(()->new ApiException("Category not found", HttpStatus.BAD_REQUEST));

        User user = this.userRepository.findById(userId).orElseThrow(()->new ApiException("User not found", HttpStatus.BAD_REQUEST));
        Budget budget = BudgetMapper.toEntity(budgetRequest, category, user);

        this.budgetRepository.save(budget);
        return "Budget saved";
    }

    public String update(Long id, BudgetRequest budgetRequest, String token) {
        Long userId = jwtService.extractUserId(token);
        Budget foundBudget = this.budgetRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ApiException("Budget not found", HttpStatus.BAD_REQUEST));

        foundBudget.setAmountLimit(budgetRequest.getAmountLimit());
        foundBudget.setMonth(budgetRequest.getMonth());
        this.budgetRepository.save(foundBudget);
        return "Budget updated";
    }

    public String delete(Long id, String token) {
        Long userId = jwtService.extractUserId(token);
        Budget foundBudget =
                this.budgetRepository
                        .findByIdAndUserId(id, userId)
                        .orElseThrow(() -> new ApiException("Budget not found", HttpStatus.BAD_REQUEST));
        this.budgetRepository.delete(foundBudget);
        return "Budget deleted";
    }
}
