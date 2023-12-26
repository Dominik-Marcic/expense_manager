package com.devot.expense_manager.service;

import org.springframework.security.core.userdetails.User;

import java.security.Principal;

public interface UserService {
    void newTransaction(double amount, User user);

    void updateTransaction(double previousAmount, double newAmount, User user);
}
