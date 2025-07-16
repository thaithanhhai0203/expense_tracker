package com.learn.expense_tracker.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.expense_tracker.category.request.CategoryRequest;
import com.learn.expense_tracker.category.response.CategoryResponse;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.config.TestConfig;
import com.learn.expense_tracker.user.User;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

@WebMvcTest(CategoryController.class)
@Import(TestConfig.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HandlerMethodArgumentResolver currentUserArgumentResolver;

    private User mockUser() {
        User user = new User();
        user.setId(1L);
        return user;
    }

    @BeforeEach
    void setup() throws Exception {
        when(currentUserArgumentResolver.supportsParameter(Mockito.any())).thenReturn(true);
        when(currentUserArgumentResolver.resolveArgument(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())
        ).thenReturn(mockUser());
    }

    @Test
    void getAll_shouldReturnList() throws Exception {
        List<CategoryResponse> mockList = List.of(CategoryResponse.builder().id(1L).name("Food").icon("🍔").build());

        when(categoryService.getAll(1L)).thenReturn(mockList);

        mockMvc
                .perform(get("/api/categories").requestAttr("currentUser", mockUser())) // @CurrentUser
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Food"));
    }

    @Test
    void create_shouldReturnSuccessCode() throws Exception {
        CategoryRequest request = CategoryRequest.builder().name("Transport").icon("🚗").build();

        when(categoryService.save(any(CategoryRequest.class), Mockito.eq(1L)))
                .thenReturn(SuccessCode.CATEGORY_CREATED);

        mockMvc
                .perform(
                        post("/api/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .requestAttr("currentUser", mockUser())
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        when(categoryService.getById(100L, 1L))
                .thenReturn(CategoryResponse.builder().id(100L).name("Salary").icon("💵").build());
    }

    @Test
    void getById_shouldReturnCategory() throws Exception {
        when(categoryService.getById(100L, 1L))
                .thenReturn(CategoryResponse.builder().id(1L).name("Food").icon("🍔").build());

        mockMvc
                .perform(get("/api/categories/100").requestAttr("currentUser", mockUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Salary"));
    }

    @Test
    void update_shouldReturnSuccess() throws Exception {
        CategoryRequest updateRequest = CategoryRequest.builder().name("Transport").icon("🚗").build();

        when(categoryService.update(Mockito.eq(100L), any(CategoryRequest.class), Mockito.eq(1L)))
                .thenReturn(SuccessCode.CATEGORY_UPDATED);

        mockMvc
                .perform(
                        put("/api/categories/100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .requestAttr("currentUser", mockUser())
                                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(SuccessCode.CATEGORY_UPDATED.getMessage()));
    }

    @Test
    void delete_shouldReturnSuccess() throws Exception {
        when(categoryService.delete(100L, 1L)).thenReturn(SuccessCode.CATEGORY_DELETED);

        mockMvc
                .perform(delete("/api/categories/100").requestAttr("currentUser", mockUser()))
                .andExpect(status().isOk())
                .andExpect(content().string(SuccessCode.CATEGORY_DELETED.getMessage()));
    }
}
