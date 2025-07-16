package com.learn.expense_tracker.category;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.learn.expense_tracker.category.request.CategoryRequest;
import com.learn.expense_tracker.category.response.CategoryResponse;
import com.learn.expense_tracker.common.dto.ErrorCode;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.common.exception.ApiException;
import com.learn.expense_tracker.user.User;
import com.learn.expense_tracker.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
  @Mock private CategoryRepository categoryRepository;

  @Mock private UserRepository userRepository;

  @InjectMocks private CategoryService categoryService;

  @Test
  void save_shouldCreateCategory_whenNotExists() {
    // Given
    Long userId = 1L;
    CategoryRequest request = new CategoryRequest("name", "icon");
    User mockUser = new User();
    mockUser.setId(userId);

    when(categoryRepository.findByNameAndUserId("name", userId)).thenReturn(Optional.empty());
    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

    // When
    SuccessCode result = categoryService.save(request, userId);

    // Then
    assertEquals(SuccessCode.CATEGORY_CREATED, result);
    verify(categoryRepository).save(any(Category.class));
  }

  @Test
  void save_shouldThrowException_whenCategoryAlreadyExists() {
    // Given
    Long userId = 1L;
    CategoryRequest request = new CategoryRequest("Food", "icon");
    Category existingCategory = new Category();

    // When
    when(categoryRepository.findByNameAndUserId("Food", userId))
        .thenReturn(Optional.of(existingCategory));

    // Then
    ApiException exception =
        assertThrows(ApiException.class, () -> categoryService.save(request, userId));
    assertEquals(ErrorCode.CATEGORY_ALREADY_EXISTS.getMessage(), exception.getMessage());

    verify(categoryRepository, never()).save(any());
  }

  @Test
  void getById_shouldReturnCategory_whenExists() {
    // Given
    Long categoryId = 10L;
    Long userId = 1L;
    Category category = new Category();
    category.setId(categoryId);
    category.setName("Food");

    // When
    when(categoryRepository.findByIdAndUserId(categoryId, userId))
        .thenReturn(Optional.of(category));

    CategoryResponse response = categoryService.getById(categoryId, userId);

    // Then
    assertEquals(response.getId(), categoryId);
    assertEquals(response.getName(), "Food");
  }

  @Test
  void getById_shouldThrowException_whenNotFound() {
    // When
    when(categoryRepository.findByIdAndUserId(99L, 1L)).thenReturn(Optional.empty());

    // Then
    assertThrows(ApiException.class, () -> categoryService.getById(99L, 1L));
  }

  @Test
  void update_shouldUpdateCategory_whenExists() {
    // Given
    Long categoryId = 1L;
    Long userId = 1L;
    Category existing = new Category();
    existing.setId(categoryId);
    existing.setName("Old Name");
    existing.setIcon("🔥");

    // When
    when(categoryRepository.findByIdAndUserId(categoryId, userId))
        .thenReturn(Optional.of(existing));
    CategoryRequest updateRequest = new CategoryRequest("New Name", "📈");

    when(categoryRepository.findByIdAndUserId(categoryId, userId))
        .thenReturn(Optional.of(existing));

    SuccessCode result = categoryService.update(categoryId, updateRequest, userId);

    // Then
    assertEquals(result, SuccessCode.CATEGORY_UPDATED);
    assertEquals(existing.getName(), "New Name");
    assertEquals(existing.getIcon(), "📈");
  }

  @Test
  void delete_shouldRemoveCategory_whenExists() {
    // Given
    Long categoryId = 1L;
    Long userId = 1L;
    Category existing = new Category();
    existing.setId(categoryId);

    // When
    when(categoryRepository.findByIdAndUserId(categoryId, userId))
        .thenReturn(Optional.of(existing));

    SuccessCode result = categoryService.delete(categoryId, userId);

    // Then
    assertEquals(result, SuccessCode.CATEGORY_DELETED);
    verify(categoryRepository).delete(existing);
  }
}
