package com.learn.expense_tracker.category.mapper;

import com.learn.expense_tracker.category.Category;
import com.learn.expense_tracker.category.request.CategoryRequest;
import com.learn.expense_tracker.category.response.CategoryResponse;
import com.learn.expense_tracker.user.User;

public class CategoryMapper {
  public static Category toEntity(CategoryRequest categoryRequest, User user) {
    Category category = new Category();
    category.setName(categoryRequest.getName());
    category.setIcon(categoryRequest.getIcon());
    category.setUser(user);
    return category;
  }

  public static CategoryResponse toResponse(Category category) {
    CategoryResponse response = new CategoryResponse();
    response.setId(category.getId());
    response.setName(category.getName());
    response.setIcon(category.getIcon());
    response.setCreatedAt(category.getCreatedAt());
    response.setUpdatedAt(category.getUpdatedAt());
    return response;
  }
}
