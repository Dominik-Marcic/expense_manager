package com.devot.expense_manager.dto;

import java.util.Date;

public class ExpenseResponseDto {
    private int id;
    private String description;
    private double amount;
    private Date date;
    private CategoryDto category;

    public ExpenseResponseDto(int id, String description, double amount, Date date, CategoryDto categoryDto) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = categoryDto;
    }

    public ExpenseResponseDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public CategoryDto getCategoryDto() {
        return category;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.category = categoryDto;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
