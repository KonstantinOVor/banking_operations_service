package com.example.test.repository;

import com.example.test.model.BankAccount;
import com.example.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByUser(User user);
}
