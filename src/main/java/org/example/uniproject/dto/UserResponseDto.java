package org.example.uniproject.dto;

import lombok.Value;
import org.example.uniproject.entity.Role;

import java.time.LocalDate;

@Value
public class UserResponseDto {
    Integer id;
    String username;
    String lastName;
    String firstName;
    LocalDate birthDate;
    Role role;
}
