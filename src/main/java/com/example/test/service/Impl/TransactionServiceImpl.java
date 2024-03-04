package com.example.test.service.Impl;

import com.example.test.dto.UserDto;
import com.example.test.exception.ResourceNotFoundException;
import com.example.test.model.BankAccount;
import com.example.test.model.TransactionUser;
import com.example.test.model.User;
import com.example.test.model.message.ProgramResponse;
import com.example.test.repository.BankAccountRepository;
import com.example.test.repository.TransactionRepository;
import com.example.test.repository.UserRepository;
import com.example.test.service.BankAccountService;
import com.example.test.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountService bankAccountService;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;

    @Override
    public List<TransactionUser> getTransactionsByAccounts(UserDto sourceAccountDto, UserDto destinationAccountDto) {
        BankAccount sourceAccount = bankAccountService.findById(sourceAccountDto);
        BankAccount destinationAccount = bankAccountService.findById(destinationAccountDto);

        if (sourceAccount == null || destinationAccount == null) {
            throw new ResourceNotFoundException(ProgramResponse.BANK_ACCOUNT_NOT_FOUND.getDescription());
        }
        List<TransactionUser> transactions = transactionRepository.findBySourceAccountOrDestinationAccount(sourceAccount, destinationAccount);
        log.debug(ProgramResponse.TRANSACTIONS_FOUND.getDescription(), transactionRepository.findBySourceAccountOrDestinationAccount(sourceAccount, destinationAccount));
        return transactions;
    }

    @Override
    public TransactionUser transferMoney(Double amount, UserDto sourceUserDto, UserDto destinationUserDto) {
        User sourceUser = userRepository.findById(sourceUserDto.getId()).get();
        User destinationUser = userRepository.findById(destinationUserDto.getId()).get();
        BankAccount sourceAccount = bankAccountRepository.findByUser(sourceUser);
        BankAccount destinationAccount = bankAccountRepository.findByUser(destinationUser);

        if (sourceAccount == null || destinationAccount == null) {
            throw new ResourceNotFoundException(ProgramResponse.BANK_ACCOUNT_NOT_FOUND.getDescription());
        }

        if (sourceAccount.getBalance() < amount) {
            throw new ResourceNotFoundException(ProgramResponse.INSUFFICIENT_BALANCE.getDescription());
        }

        updateBalances(sourceAccount, destinationAccount, amount);
        TransactionUser transaction = createAndSaveTransaction(sourceAccount, destinationAccount, amount);
        log.debug(ProgramResponse.TRANSACTION_CREATED.getDescription(), transaction);
        return transaction;
    }

    @Transactional
    private void updateBalances(BankAccount sourceAccount, BankAccount destinationAccount, Double amount) {
        Double sourceBalance = sourceAccount.getBalance();
        Double destinationBalance = destinationAccount.getBalance();
        LocalDateTime updatedSourceTimestamp = LocalDateTime.now();
        sourceAccount.setCreatedDate(updatedSourceTimestamp);
        destinationAccount.setCreatedDate(updatedSourceTimestamp);

        if (sourceBalance - amount < 0 || amount < 0) {
            throw new ResourceNotFoundException(ProgramResponse.INSUFFICIENT_BALANCE.getDescription());
        }

        sourceAccount.setBalance(sourceBalance - amount);

        Double newSourceBalance = sourceBalance - amount;
        Double newDestinationBalance = destinationBalance + amount;

        sourceAccount.setBalance(newSourceBalance);
        log.debug(ProgramResponse.BALANCE_UPDATED.getDescription(), sourceAccount.getUser());
        destinationAccount.setBalance(newDestinationBalance);
        log.debug(ProgramResponse.BALANCE_UPDATED.getDescription(), destinationAccount.getUser());

        if (!updatedSourceTimestamp.equals(sourceAccount.getCreatedDate()) || !updatedSourceTimestamp.equals(destinationAccount.getCreatedDate())) {
            throw new OptimisticLockingFailureException(ProgramResponse.OPTIMISTIC_LOCKING_FAILURE.getDescription());
        }

        bankAccountRepository.save(sourceAccount);
        bankAccountRepository.save(destinationAccount);
    }

    @Transactional
    private TransactionUser createAndSaveTransaction(BankAccount sourceAccount, BankAccount destinationAccount, Double amount) {
        TransactionUser transaction = new TransactionUser();
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
        return transaction;
    }
}