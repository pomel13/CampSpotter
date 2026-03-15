package ru.campspotter.backend.dto.auth;

/**
 * Authentication response after login.
 */
public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
