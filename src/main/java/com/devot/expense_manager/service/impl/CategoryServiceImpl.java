package com.devot.expense_manager.service.impl;

import com.devot.expense_manager.dto.CategoryDto;
import com.devot.expense_manager.dto.GenericResponse;
import com.devot.expense_manager.model.Category;
import com.devot.expense_manager.model.User;
import com.devot.expense_manager.repository.CategoryRepository;
import com.devot.expense_manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        categoryRepository.save(category);

        CategoryDto categoryResponse = new CategoryDto();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());

        return categoryResponse;
    }

    @Override
    public GenericResponse getAllCategories(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<Category> categoryList = categories.getContent();
        List<CategoryDto> responseContent = categoryList.stream().map(category -> mapForDto(category)).collect(Collectors.toList());

        GenericResponse<CategoryDto> genericResponse = new GenericResponse<>();
        genericResponse.setContentList(responseContent);
        genericResponse.setPageNo(categories.getNumber());
        genericResponse.setPageSize(categories.getSize());
        genericResponse.setTotalElements(categories.getTotalElements());
        genericResponse.setTotalPages(categories.getTotalPages());
        genericResponse.setLast(categories.isLast());

        return genericResponse;
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        return mapForDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryDto.getName());

        Category updatedCategory = categoryRepository.save(category);
        return mapForDto(updatedCategory);
    }

    @Override
    public void deleteCategoryId(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto mapForDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    @Override
    public Category mapForEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }
}
