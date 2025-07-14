package com.learn.expense_tracker.category;

import com.learn.expense_tracker.category.request.CategoryRequest;
import com.learn.expense_tracker.category.response.CategoryResponse;
import com.learn.expense_tracker.common.annotation.CurrentUser;
import com.learn.expense_tracker.common.annotation.RequireRoles;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category", description = "Info Category")
@RestController
@RequestMapping("/api/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Operation(summary = "Get list of categories")
  @RequireRoles({"ADMIN", "USER"})
  @GetMapping
  public List<CategoryResponse> getAll(@CurrentUser User user) {
    return this.categoryService.getAll(user.getId());
  }

  @Operation(summary = "Create category")
  @RequireRoles({"ADMIN", "USER"})
  @PostMapping
  public SuccessCode create(@RequestBody CategoryRequest categoryRequest, @CurrentUser User user) {
    return this.categoryService.save(categoryRequest, user.getId());
  }

  @Operation(summary = "Get category detail")
  @RequireRoles({"ADMIN", "USER"})
  @GetMapping("/{id}")
  public CategoryResponse getById(@PathVariable Long id, @CurrentUser User user) {
    return this.categoryService.getById(id, user.getId());
  }

  @Operation(summary = "Update category")
  @RequireRoles({"ADMIN", "USER"})
  @PutMapping("/{id}")
  public SuccessCode update(
      @PathVariable Long id, @RequestBody CategoryRequest categoryRequest, @CurrentUser User user) {
    return this.categoryService.update(id, categoryRequest, user.getId());
  }

  @Operation(summary = "Delete category")
  @RequireRoles({"ADMIN", "USER"})
  @DeleteMapping("/{id}")
  public SuccessCode delete(@PathVariable Long id, @CurrentUser User user) {
    return this.categoryService.delete(id, user.getId());
  }
}
