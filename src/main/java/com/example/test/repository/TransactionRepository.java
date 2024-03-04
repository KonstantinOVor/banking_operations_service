package com.example.test.repository;

import com.example.test.model.BankAccount;
import com.example.test.model.TransactionUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionUser, Long> {
    List<TransactionUser> findBySourceAccountOrDestinationAccount(BankAccount sourceAccount, BankAccount destinationAccount);
}
