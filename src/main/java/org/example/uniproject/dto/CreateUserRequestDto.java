package org.example.uniproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.time.LocalDate;

@Value
public class CreateUserRequestDto {
    @NotBlank(message = "Username must not be blank")
    @Email(message = "Username must be a valid email")
    @Size(max = 128, message = "Username must not be longer than 128 characters")
    String username;

    @Size(max = 128, message = "First name must not be longer than 128 characters")
    String firstName;

    @Size(max = 128, message = "Last name must not be longer than 128 characters")
    String lastName;

    @Past(message = "Birth date must be in the past")
    LocalDate birthDate;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 64, message = "Password length must be between 6 and 64 characters")
    String rawPassword;
}
