package com.learn.expense_tracker.category;

import com.learn.expense_tracker.category.mapper.CategoryMapper;
import com.learn.expense_tracker.category.request.CategoryRequest;
import com.learn.expense_tracker.category.response.CategoryResponse;
import com.learn.expense_tracker.common.dto.ErrorCode;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.common.exception.ApiException;
import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.user.User;
import com.learn.expense_tracker.user.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  public CategoryService(
      CategoryRepository categoryRepository, JwtService jwtService, UserRepository userRepository) {
    this.categoryRepository = categoryRepository;
    this.userRepository = userRepository;
  }

  public List<CategoryResponse> getAll(Long userId) {
    List<Category> categories = this.categoryRepository.findByUserId(userId);
    return categories.stream().map(CategoryMapper::toResponse).collect(Collectors.toList());
  }

  public CategoryResponse getById(Long id, Long userId) {
    Category existingCategory =
        this.categoryRepository
            .findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND));
    return CategoryMapper.toResponse(existingCategory);
  }

  public SuccessCode save(CategoryRequest categoryRequest, Long userId) {
    Optional<Category> existingUser =
        this.categoryRepository.findByNameAndUserId(categoryRequest.getName(), userId);
    if (existingUser.isPresent()) {
      throw new ApiException(ErrorCode.CATEGORY_ALREADY_EXISTS);
    }

    User user =
        this.userRepository
            .findById(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    Category category = CategoryMapper.toEntity(categoryRequest, user);
    this.categoryRepository.save(category);
    return SuccessCode.CATEGORY_CREATED;
  }

  public SuccessCode update(Long id, CategoryRequest categoryRequest, Long userId) {
    Category foundCategory =
        this.categoryRepository
            .findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND));
    foundCategory.setName(categoryRequest.getName());
    foundCategory.setIcon(categoryRequest.getIcon());
    this.categoryRepository.save(foundCategory);
    return SuccessCode.CATEGORY_UPDATED;
  }

  public SuccessCode delete(Long id, Long userId) {
    Category foundCategory =
        this.categoryRepository
            .findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND));
    this.categoryRepository.delete(foundCategory);
    return SuccessCode.CATEGORY_DELETED;
  }
}
