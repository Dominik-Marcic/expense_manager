package com.devot.expense_manager.service.impl;

import com.devot.expense_manager.dto.*;
import com.devot.expense_manager.model.Expense;
import com.devot.expense_manager.repository.ExpenseRepository;
import com.devot.expense_manager.service.CategoryService;
import com.devot.expense_manager.service.ExpenseService;
import com.devot.expense_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;
    private CategoryService categoryService;
    private UserService userService;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, CategoryService categoryService, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Override
    public GenericResponse getAllExpenses(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Expense> expenses = expenseRepository.findAll(pageable);
        List<Expense> expenseList = expenses.getContent();
        List<ExpenseResponseDto> responseContent = expenseList.stream().map(expense -> mapForDto(expense)).collect(Collectors.toList());

        GenericResponse<ExpenseResponseDto> genericResponse = new GenericResponse<>();
        genericResponse.setContentList(responseContent);
        genericResponse.setPageNo(expenses.getNumber());
        genericResponse.setPageSize(expenses.getSize());
        genericResponse.setTotalElements(expenses.getTotalElements());
        genericResponse.setTotalPages(expenses.getTotalPages());
        genericResponse.setLast(expenses.isLast());

        return genericResponse;
    }

    @Override
    public ExpenseResponseDto getExpenseById(int id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        return mapForDto(expense);
    }

    @Override
    @Transactional
    public ExpenseResponseDto createExpense(ExpenseDto expenseDto, User user) {
        Expense expense = new Expense();
        expense.setDescription(expenseDto.getDescription());
        expense.setAmount(expenseDto.getAmount());
        if (expenseDto.getDate() != null)
            expense.setDate(expenseDto.getDate());
        else
            expense.setDate(new Date());

        CategoryDto categoryById = categoryService.getCategoryById(expenseDto.getCategoryId());
        expense.setCategory(categoryService.mapForEntity(categoryById));

        expenseRepository.save(expense);

        userService.newTransaction(expense.getAmount(), user);

        return mapForDto(expense);
    }

    @Override
    public ExpenseResponseDto updateExpense(ExpenseDto expenseDto, User user) {
        Expense expense = expenseRepository.findById(expenseDto.getId()).orElseThrow(() -> new RuntimeException("Expense not found"));

        double previousAmount = expense.getAmount();

        expense.setDescription(expenseDto.getDescription());
        expense.setAmount(expenseDto.getAmount());
        expense.setDate(expenseDto.getDate());

        CategoryDto categoryById = categoryService.getCategoryById(expenseDto.getCategoryId());

        expense.setCategory(categoryService.mapForEntity(categoryById));

        Expense updatedExpense = expenseRepository.save(expense);

        userService.updateTransaction(previousAmount, updatedExpense.getAmount(), user);

        return mapForDto(updatedExpense);
    }

    @Override
    public void deleteExpenseId(int id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        expenseRepository.delete(expense);
    }

    @Override
    public List<ExpenseResponseDto> filterByCategory(int id) {
        List<Expense> expensesList = expenseRepository.findAll().stream().filter(expense -> expense.getCategory().getId() == id).collect(Collectors.toList());
        List<ExpenseResponseDto> filteredList = expensesList.stream().map(expense -> mapForDto(expense)).collect(Collectors.toList());
        return filteredList;
    }

    @Override
    public List<ExpenseResponseDto> filterByPrice(boolean isAscending) {
        List<Expense> expenses;

        if (isAscending)
            expenses = expenseRepository.findAll().stream().sorted(Comparator.comparingDouble(Expense::getAmount)).collect(Collectors.toList());
        else
            expenses = expenseRepository.findAll().stream().sorted(Comparator.comparingDouble(Expense::getAmount).reversed()).collect(Collectors.toList());

        List<ExpenseResponseDto> filteredList = expenses.stream().map(expense -> mapForDto(expense)).collect(Collectors.toList());
        return filteredList;
    }

    @Override
    public List<ExpenseResponseDto> getExpensesByDate(LocalDate startDate, LocalDate endDate) {
        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endTimestamp = Timestamp.valueOf(endDate.atStartOfDay());
        List<Expense> expenses = expenseRepository.findByDateBetween(startTimestamp, endTimestamp);

        return expenses.stream()
                .map(this::mapForDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MoneyStatisticsDto getMoneyStatisticsLastMonth() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);

        // Calculate the first day of the next month
        LocalDate endDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);

        // Convert LocalDates to Timestamps
        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endTimestamp = Timestamp.valueOf(endDate.atStartOfDay());

        // Fetch expenses within the date range
        List<Expense> expenses = expenseRepository.findByDateBetween(startTimestamp, endTimestamp);

        // Calculate money earned and spent
        double moneyEarned = expenses.stream()
                .filter(expense -> expense.getAmount() > 0)
                .mapToDouble(Expense::getAmount)
                .sum();

        double moneySpent = expenses.stream()
                .filter(expense -> expense.getAmount() < 0)
                .mapToDouble(Expense::getAmount)
                .sum();

        return new MoneyStatisticsDto(moneyEarned, moneySpent);
    }

    private ExpenseResponseDto mapForDto(Expense expense) {
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setId(expense.getId());
        expenseResponseDto.setDescription(expense.getDescription());
        expenseResponseDto.setAmount(expense.getAmount());
        expenseResponseDto.setDate(expense.getDate());
        expenseResponseDto.setCategoryDto(categoryService.mapForDto(expense.getCategory()));
        return expenseResponseDto;
    }
}
