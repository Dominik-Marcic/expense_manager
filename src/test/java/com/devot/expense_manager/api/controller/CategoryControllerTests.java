package com.devot.expense_manager.api.controller;

import com.devot.expense_manager.dto.CategoryDto;
import com.devot.expense_manager.dto.GenericResponse;
import com.devot.expense_manager.model.Category;
import com.devot.expense_manager.restcontroller.CategoryController;
import com.devot.expense_manager.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private Category category;
    private CategoryDto categoryDto;

    @BeforeEach
    public void init() {
        category = new Category();
        category.setName("Test category 123");
        categoryDto = new CategoryDto();
        categoryDto.setName("Test category 123");
    }

    @Test
    public void CategoryController_CreateController_ReturnCreated() throws Exception {
        given(categoryService.createCategory(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(categoryDto.getName())));
    }

    @Test
    public void CategoryController_GetAllCategories_ReturnResponseDto() throws Exception {
        GenericResponse<CategoryDto> genericResponse = new GenericResponse<>();
        genericResponse.setPageSize(10);
        genericResponse.setLast(true);
        genericResponse.setPageNo(1);
        genericResponse.setContentList(Arrays.asList(categoryDto));
        when(categoryService.getAllCategories(1, 10)).thenReturn(genericResponse);

        ResultActions response = mockMvc.perform(get("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNum", "1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.contentList.size()", CoreMatchers.is(genericResponse.getContentList().size())));
    }

}
