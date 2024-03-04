//package com.example.test.controller;
//
//import com.example.test.model.BankAccount;
//import com.example.test.service.BankAccountService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/bankAccounts")
//public class BankAccountController {
//
//    private final BankAccountService bankAccountService;
//
//    @PostMapping("/create")
//    public ResponseEntity<BankAccount> createBankAccount() {
//        BankAccount newBankAccount = bankAccountService.createBankAccount(bankAccountDto);
//        return ResponseEntity.ok(newBankAccount);
//    }
//}