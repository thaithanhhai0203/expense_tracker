package com.learn.expense_tracker.category.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Icon must not be blank")
    private String icon;
}
