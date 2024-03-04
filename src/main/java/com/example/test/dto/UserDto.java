package com.example.test.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String fullName;
    private String dateOfBirth;
    private Double balance;
}
