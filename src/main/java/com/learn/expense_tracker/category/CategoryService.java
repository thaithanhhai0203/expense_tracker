package com.learn.expense_tracker.category;

import com.learn.expense_tracker.category.mapper.CategoryMapper;
import com.learn.expense_tracker.category.request.CategoryRequest;
import com.learn.expense_tracker.category.response.CategoryResponse;
import com.learn.expense_tracker.common.exception.ApiException;
import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.user.User;
import com.learn.expense_tracker.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository,  JwtService jwtService, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public List<CategoryResponse> getAll() {

        List<Category> categories = this.categoryRepository.findAll();

        return categories.stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getById(Long id) {
        Category foundCategory = this.categoryRepository.findById(id).orElseThrow(()->new ApiException("Category not found"));
        return CategoryMapper.toResponse(foundCategory);
    }

    public String save(CategoryRequest categoryRequest, String token) {
        Optional<Category> foundUser = this.categoryRepository.findByName(categoryRequest.getName());
        if(foundUser.isPresent()) {
            return "Category already exists";
        }

        Long userId = jwtService.extractUserId(token);
        User user = this.userRepository.findById(userId).orElseThrow(()->new ApiException("User not found"));
        Category category = CategoryMapper.toEntity(categoryRequest, user);
        this.categoryRepository.save(category);
        return "Category saved";
    }

    public String update(Long id, CategoryRequest categoryRequest) {
        Category foundCategory = this.categoryRepository.findById(id).orElseThrow(()->new ApiException("Category not found"));
        foundCategory.setName(categoryRequest.getName());
        foundCategory.setIcon(categoryRequest.getIcon());
        this.categoryRepository.save(foundCategory);
        return "Category updated";
    }

    public String delete(Long id) {
        Category foundCategory = this.categoryRepository.findById(id).orElseThrow(()->new ApiException("Category not found"));
        this.categoryRepository.delete(foundCategory);
        return "Category deleted";
    }

}
