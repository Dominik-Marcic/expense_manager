package com.devot.expense_manager.restcontroller;

import com.devot.expense_manager.dto.ExpenseDto;
import com.devot.expense_manager.dto.ExpenseResponseDto;
import com.devot.expense_manager.dto.GenericResponse;
import com.devot.expense_manager.dto.MoneyStatisticsDto;
import com.devot.expense_manager.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpensesController {

    private ExpenseService expenseService;

    @Autowired
    public ExpensesController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("expenses")
    public ResponseEntity<GenericResponse> getExpenses(
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(expenseService.getAllExpenses(pageNum, pageSize), HttpStatus.OK);
    }

    @GetMapping("expenses/{id}")
    public ResponseEntity<ExpenseResponseDto> getExpense(@PathVariable int id) {
        return new ResponseEntity<>(expenseService.getExpenseById(id), HttpStatus.OK);
    }

    @PostMapping("expenses/create")
    public ResponseEntity<ExpenseResponseDto> createExpense(@RequestBody ExpenseDto expenseDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return new ResponseEntity<>(expenseService.createExpense(expenseDto, user), HttpStatus.OK);
    }

    @PutMapping("expenses/update")
    public ResponseEntity<ExpenseResponseDto> updateExpense(@RequestBody ExpenseDto expenseDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        ExpenseResponseDto response = expenseService.updateExpense(expenseDto, user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("expenses/{id}/delete")
    public ResponseEntity<String> deleteExpense(@PathVariable int id) {
        expenseService.deleteExpenseId(id);
        return new ResponseEntity<>("Expense deleted", HttpStatus.OK);
    }

    @GetMapping("expenses/filter/categoryId/{id}")
    public ResponseEntity<List<ExpenseResponseDto>> filterExpensesByCategory(@PathVariable int id) {
        List<ExpenseResponseDto> expenseResponseDtos = expenseService.filterByCategory(id);
        return new ResponseEntity<>(expenseResponseDtos, HttpStatus.OK);
    }

    @GetMapping("expenses/filter/price/{isAscending}")
    public ResponseEntity<List<ExpenseResponseDto>> filterExpensesByPrice(@PathVariable boolean isAscending) {
        List<ExpenseResponseDto> expenseResponseDtos = expenseService.filterByPrice(isAscending);
        return new ResponseEntity<>(expenseResponseDtos, HttpStatus.OK);
    }

    @GetMapping("expenses/filter/dateBetween/")
    public ResponseEntity<List<ExpenseResponseDto>> getExpensesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ExpenseResponseDto> filteredExpenses = expenseService.getExpensesByDate(startDate, endDate);
        return new ResponseEntity<>(filteredExpenses, HttpStatus.OK);
    }

    @GetMapping("expenses/moneyStatisticsLastMonth")
    public ResponseEntity<MoneyStatisticsDto> getMoneyStatisticsLastMonth() {
        MoneyStatisticsDto moneyStatistics = expenseService.getMoneyStatisticsLastMonth();
        return new ResponseEntity<>(moneyStatistics, HttpStatus.OK);
    }
}
