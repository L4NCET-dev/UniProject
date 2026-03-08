package org.example.uniproject.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class UserFilter {
    String username;
    String firstName;
    String lastName;
    LocalDate birthDate;
}
