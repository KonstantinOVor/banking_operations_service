package com.example.test.service.Impl;

import com.example.test.dto.UserDto;
import com.example.test.exception.ResourceNotFoundException;
import com.example.test.model.BankAccount;
import com.example.test.model.User;
import com.example.test.model.message.ProgramResponse;
import com.example.test.repository.BankAccountRepository;
import com.example.test.repository.TransactionRepository;
import com.example.test.repository.UserRepository;
import com.example.test.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    public BankAccount createBankAccount(User user,  Double balance) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBalance(balance);
        log.debug(ProgramResponse.BANK_ACCOUNT_CREATED.getDescription(), bankAccount);
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    @Transactional
    public BankAccount updateBankAccount(Double amount, UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).get();
        BankAccount bankAccount = bankAccountRepository.findByUser(user);
        if (bankAccount != null) {
            bankAccount.setBalance(amount);
            log.debug(ProgramResponse.BANK_ACCOUNT_UPDATED.getDescription(), bankAccount);
            return bankAccountRepository.save(bankAccount);
        } else {
            throw new ResourceNotFoundException(ProgramResponse.BANK_ACCOUNT_NOT_FOUND.getDescription());
        }
    }

    @Override
    public void deleteBankAccount(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).get();
        BankAccount bankAccount = bankAccountRepository.findByUser(user);
        if (bankAccount != null) {
            bankAccountRepository.delete(bankAccount);
            log.debug(ProgramResponse.BANK_ACCOUNT_DELETED.getDescription(), bankAccount);
        } else {
            throw new ResourceNotFoundException(ProgramResponse.BANK_ACCOUNT_NOT_FOUND.getDescription());
        }
    }

    @Override
    public BankAccount findById(UserDto userDto) {
        return bankAccountRepository.findById(userDto.getId()).orElse(null);
    }
}
