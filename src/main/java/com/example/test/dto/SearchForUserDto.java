package com.example.test.dto;

import lombok.Data;

@Data
public class SearchForUserDto {
    private String dateOfBirth;
    private int page;
    private int pageSize;
}
