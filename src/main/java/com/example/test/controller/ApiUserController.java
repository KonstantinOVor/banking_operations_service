package com.example.test.controller;

import com.example.test.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class ApiUserController {
    private final UserService userService;

    @PutMapping("/phones")
    public ResponseEntity<String> updatePhoneContact(@RequestParam String registeredPhone, @RequestParam String newPhone) {
        try {
            userService.updatePhoneContact(registeredPhone, newPhone);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok().body("Телефон обновлен");
    }

    @PutMapping("/emails")
    public ResponseEntity<String> updateEmailContact(@RequestParam String registeredEmail, @RequestParam String newEmail) {
        try {
            userService.updateEmailContact(registeredEmail, newEmail);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok().body("Email обновлен");
    }

    @DeleteMapping("/phones")
    public ResponseEntity<String> deletePhoneContact(@RequestParam String phone) {
        try {
            userService.deletePhoneContact(phone);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok().body("Телефон удален");
    }

    @DeleteMapping("/emails")
    public ResponseEntity<String> deleteEmailContact(@RequestParam String email) {
        try {
            userService.deleteEmailContact(email);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok().body("Email удален");
    }
}

