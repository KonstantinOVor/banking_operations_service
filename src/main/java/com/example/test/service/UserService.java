package com.example.test.service;

import com.example.test.dto.SearchForUserDto;
import com.example.test.dto.UserDto;
import com.example.test.model.User;
import java.util.List;

public interface UserService {
    User createUser(UserDto user);
    User findById(Long userId);
    User findByFullName(String name);
    User findByPhone(String phone);
    User findByEmail(String email);
    List<User> findUsersByDateOfBirth(SearchForUserDto searchForUserDto);
    void updatePhoneContact(String existingUser, String contactType);
    void updateEmailContact(String registeredEmail, String newEmail);
    void deleteEmailContact(String email);
    void deletePhoneContact(String phone);

}
