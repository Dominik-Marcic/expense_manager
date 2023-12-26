package com.devot.expense_manager.service;

import com.devot.expense_manager.dto.CategoryDto;
import com.devot.expense_manager.dto.GenericResponse;
import com.devot.expense_manager.model.Category;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    GenericResponse getAllCategories(int pageNum, int pageSize);
    CategoryDto getCategoryById(int id);
    CategoryDto updateCategory(CategoryDto categoryDto, int id);
    void deleteCategoryId(int id);
    CategoryDto mapForDto(Category category);
    Category mapForEntity(CategoryDto categoryDto);
}
