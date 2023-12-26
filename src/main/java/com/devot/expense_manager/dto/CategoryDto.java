package com.devot.expense_manager.dto;

import com.devot.expense_manager.model.User;

public class CategoryDto {
    private int id;
    private String name;

    public CategoryDto(int id, String name, User user) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
