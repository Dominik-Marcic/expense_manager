package com.devot.expense_manager.restcontroller;

import com.devot.expense_manager.dto.CategoryDto;
import com.devot.expense_manager.dto.GenericResponse;
import com.devot.expense_manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("category")
    public ResponseEntity<GenericResponse> getCategories(
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(categoryService.getAllCategories(pageNum, pageSize), HttpStatus.OK);
    }

    @GetMapping("category/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable int id) {
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    @PostMapping("category/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @PutMapping("category/{id}/update")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable int id) {
        CategoryDto response = categoryService.updateCategory(categoryDto, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("category/{id}/delete")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategoryId(id);
        return new ResponseEntity<>("Category deleted", HttpStatus.OK);
    }

}
