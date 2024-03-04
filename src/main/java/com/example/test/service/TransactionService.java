package com.example.test.service;

import com.example.test.dto.UserDto;
import com.example.test.model.TransactionUser;

import java.util.List;

public interface TransactionService {
    List<TransactionUser> getTransactionsByAccounts(UserDto sourceAccountDto, UserDto destinationAccountDto);
    TransactionUser transferMoney(Double amount, UserDto sourceUserDto, UserDto destinationUserDto);
}
