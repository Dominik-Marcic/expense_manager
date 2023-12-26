package com.devot.expense_manager.repository;

import com.devot.expense_manager.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByDateBetween(Timestamp from, Timestamp to);
}
