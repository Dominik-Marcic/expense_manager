package com.devot.expense_manager.service;

import com.devot.expense_manager.dto.ExpenseDto;
import com.devot.expense_manager.dto.ExpenseResponseDto;
import com.devot.expense_manager.dto.GenericResponse;
import com.devot.expense_manager.dto.MoneyStatisticsDto;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    GenericResponse getAllExpenses(int pageNum, int pageSize);

    ExpenseResponseDto getExpenseById(int id);

    ExpenseResponseDto createExpense(ExpenseDto expenseDto, User user);

    ExpenseResponseDto updateExpense(ExpenseDto expenseDto, User user);

    void deleteExpenseId(int id);

    List<ExpenseResponseDto> filterByCategory(int id);

    List<ExpenseResponseDto> filterByPrice(boolean isAscending);

    List<ExpenseResponseDto> getExpensesByDate(LocalDate startDate, LocalDate endDate);

    MoneyStatisticsDto getMoneyStatisticsLastMonth();
}
