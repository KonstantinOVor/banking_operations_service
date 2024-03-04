package com.example.test.service;

import com.example.test.dto.UserDto;
import com.example.test.model.BankAccount;
import com.example.test.model.User;

public interface BankAccountService {
    BankAccount createBankAccount(User user, Double balance);
    BankAccount updateBankAccount(Double balance, UserDto UserDto);
    void deleteBankAccount(UserDto UserDto);
    BankAccount findById(UserDto UserDto);
}
