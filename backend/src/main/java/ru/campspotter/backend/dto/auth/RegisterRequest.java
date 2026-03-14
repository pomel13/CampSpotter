package ru.campspotter.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    private String username;

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public @NotBlank @Size(min = 6) String getPassword() {
        return password;
    }

    public @NotBlank String getUsername() {
        return username;
    }
}
