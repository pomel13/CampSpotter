package ru.campspotter.backend.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public @NotBlank String getPassword() {
        return password;
    }
}
