package com.devot.expense_manager.dto;

public class MoneyStatisticsDto {
    private double moneyEarned;
    private double moneySpent;

    public MoneyStatisticsDto(double moneyEarned, double moneySpent) {
        this.moneyEarned = moneyEarned;
        this.moneySpent = moneySpent;
    }

    public MoneyStatisticsDto() {
    }

    public double getMoneyEarned() {
        return moneyEarned;
    }

    public void setMoneyEarned(double moneyEarned) {
        this.moneyEarned = moneyEarned;
    }

    public double getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(double moneySpent) {
        this.moneySpent = moneySpent;
    }
}
