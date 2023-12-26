package com.devot.expense_manager.service.impl;

import com.devot.expense_manager.model.User;
import com.devot.expense_manager.repository.UserRepository;
import com.devot.expense_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void newTransaction(double amount, org.springframework.security.core.userdetails.User userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        double moneyAmount = user.getMoneyAmount();
        moneyAmount -= amount;
        user.setMoneyAmount(moneyAmount);
        userRepository.save(user);
    }

    @Override
    public void updateTransaction(double previousAmount, double newAmount, org.springframework.security.core.userdetails.User userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        double moneyAmount = user.getMoneyAmount();
        double finalAmount = moneyAmount + previousAmount;
        finalAmount -= newAmount;
        user.setMoneyAmount(finalAmount);
        userRepository.save(user);
    }
}
