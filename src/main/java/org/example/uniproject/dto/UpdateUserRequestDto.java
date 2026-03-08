package org.example.uniproject.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.time.LocalDate;

@Value
public class UpdateUserRequestDto {
    @Size(max = 128, message = "First name must not be longer than 128 characters")
    String firstName;

    @Size(max = 128, message = "Last name must not be longer than 128 characters")
    String lastName;

    @Past(message = "Birth date must be in the past")
    LocalDate birthDate;
}
