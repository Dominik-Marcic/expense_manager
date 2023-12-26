package com.devot.expense_manager.api.repository;

import com.devot.expense_manager.model.Category;
import com.devot.expense_manager.model.Expense;
import com.devot.expense_manager.repository.CategoryRepository;
import com.devot.expense_manager.repository.ExpenseRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ExpenseRepositoryTests {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void ExpenseRepository_Save_ReturnSaved() {
        Expense expense = new Expense();
        expense.setAmount(10);
        expense.setDescription("Test description");
        expense.setDate(new Date());
        Category category = new Category();
        category.setName("Test category");
        Category savedCategory = categoryRepository.save(category);
        expense.setCategory(savedCategory);

        Expense savedExpense = expenseRepository.save(expense);

        Assertions.assertThat(savedExpense).isNotNull();
        Assertions.assertThat(savedExpense.getId()).isGreaterThan(0);
    }

    @Test
    public void ExpenseRepository_GetAll_ReturnMoreThanOneExpense() {
        Expense expense = new Expense();
        expense.setAmount(10);
        expense.setDescription("Test description");
        expense.setDate(new Date());
        Category category = new Category();
        category.setName("Test category");
        Category savedCategory = categoryRepository.save(category);
        expense.setCategory(savedCategory);

        Expense expense2 = new Expense();
        expense2.setAmount(10);
        expense2.setDescription("Test description 2");
        expense2.setDate(new Date());
        Category category2 = new Category();
        category2.setName("Test category 2");
        Category savedCategory2 = categoryRepository.save(category2);
        expense2.setCategory(savedCategory2);

        expenseRepository.save(expense);
        expenseRepository.save(expense2);

        List<Expense> expenseList = expenseRepository.findAll();

        Assertions.assertThat(expenseList).isNotNull();
        Assertions.assertThat(expenseList.size()).isEqualTo(2);
    }

    @Test
    public void ExpenseRepository_ExpenseDelete_ReturnExpenseIsEmpty() {
        Expense expense = new Expense();
        expense.setAmount(10);
        expense.setDescription("Test description");
        expense.setDate(new Date());
        Category category = new Category();
        category.setName("Test category");
        Category savedCategory = categoryRepository.save(category);
        expense.setCategory(savedCategory);

        expenseRepository.save(expense);

        expenseRepository.deleteById(expense.getId());
        Optional<Expense> returnedExpense = expenseRepository.findById(expense.getId());

        Assertions.assertThat(returnedExpense).isEmpty();
    }

}
