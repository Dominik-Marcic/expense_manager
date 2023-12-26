package com.devot.expense_manager.api.service;

import com.devot.expense_manager.dto.CategoryDto;
import com.devot.expense_manager.dto.GenericResponse;
import com.devot.expense_manager.model.Category;
import com.devot.expense_manager.repository.CategoryRepository;
import com.devot.expense_manager.service.impl.CategoryServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void CategoryService_CreateCategory_ReturnsCategoryDto() {
        Category category = new Category();
        category.setName("Test category 1");
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Test category 1");

        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        CategoryDto savedCategory = categoryService.createCategory(categoryDto);

        Assertions.assertThat(savedCategory).isNotNull();
    }

    @Test
    public void CategoryService_GetAllCategories_ReturnsReponseDto() {
        Page<Category> categories = Mockito.mock(Page.class);

        when(categoryRepository.findAll(Mockito.any(Pageable.class))).thenReturn(categories);

        GenericResponse savedCategories = categoryService.getAllCategories(1, 10);

        Assertions.assertThat(savedCategories).isNotNull();
    }
}
