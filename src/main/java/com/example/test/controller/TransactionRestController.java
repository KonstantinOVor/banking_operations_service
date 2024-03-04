//package com.example.test.controller;
//
//import com.example.test.model.BankAccount;
//import com.example.test.model.TransactionUser;
//import com.example.test.service.TransactionService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/transactions")
//public class TransactionRestController {
//
//    private final TransactionService transactionService;

//    @GetMapping("/accounts")
//    public ResponseEntity<List<TransactionUser>> getTransactionsByAccounts(@RequestParam Long sourceAccountId,
//                                                                           @RequestParam Long destinationAccountId) {
//        BankAccount sourceAccount = bankAccountService.findById(sourceAccountId);
//        BankAccount destinationAccount = bankAccountService.findById(destinationAccountId);
//        List<TransactionUser> transactions = transactionService.getTransactionsByAccounts(sourceAccount, destinationAccount);
//        return ResponseEntity.ok(transactions);
//    }
//
//    // Другие методы для обработки запросов
//}
