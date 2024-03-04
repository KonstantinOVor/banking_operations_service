package com.example.test.controller;

import com.example.test.dto.SearchForUserDto;
import com.example.test.dto.UserDto;
import com.example.test.model.User;
import com.example.test.service.BankAccountService;
import com.example.test.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserControllerForAdmin {

    private final UserService userService;
    private final BankAccountService bankAccountService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User newUser = userService.createUser(userDto);
        return ResponseEntity.ok(newUser);
    }
    @GetMapping("/user/getUserByName")
    public ResponseEntity<User> getUsersByFullNameStartingWith(@RequestParam String name) {
        User user = userService.findByFullName(name);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/getUserByEmail")
    public ResponseEntity<User> getUsersByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
        }

    @GetMapping("/user/getUserByPhone")
    public ResponseEntity<User> getUsersByPhone(@RequestParam String phone) {
        User user = userService.findByPhone(phone);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/getUserByDateOfBirth")
    public ResponseEntity<List<User>> getUsersByDateOfBirth(@RequestBody SearchForUserDto searchForUserDto) {
        List<User> users = userService.findUsersByDateOfBirth(searchForUserDto);
        return ResponseEntity.ok(users);
    }
}
