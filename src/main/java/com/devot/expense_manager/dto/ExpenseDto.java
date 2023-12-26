package com.devot.expense_manager.dto;

import com.devot.expense_manager.model.Category;

import java.util.Date;

public class ExpenseDto {
    private int id;
    private String description;
    private double amount;
    private Date date;
    private int categoryId;

    public ExpenseDto(int id, String description, double amount, Date date, int categoryId) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
    }

    public ExpenseDto() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

}
