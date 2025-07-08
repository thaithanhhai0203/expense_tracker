package com.learn.expense_tracker.category;

import com.learn.expense_tracker.category.request.CategoryRequest;
import com.learn.expense_tracker.category.response.CategoryResponse;
import com.learn.expense_tracker.common.dto.SuccessCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.springframework.http.HttpStatus;
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
  @GetMapping
  public List<CategoryResponse> getAll() {
    return this.categoryService.getAll();
  }

  @Operation(summary = "Create category")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SuccessCode create(
      @RequestBody CategoryRequest categoryRequest,
      @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    return this.categoryService.save(categoryRequest, token);
  }

  @Operation(summary = "Get category detail")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CategoryResponse getById(@PathVariable Long id) {
    return this.categoryService.getById(id);
  }

  @Operation(summary = "Update category")
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public SuccessCode update(
      @PathVariable Long id,
      @RequestBody CategoryRequest categoryRequest,
      @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    return this.categoryService.update(id, categoryRequest, token);
  }

  @Operation(summary = "Delete category")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public SuccessCode delete(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    return this.categoryService.delete(id, token);
  }
}
