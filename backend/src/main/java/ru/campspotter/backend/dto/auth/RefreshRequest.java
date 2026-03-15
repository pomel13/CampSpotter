package ru.campspotter.backend.dto.auth;

/**
 * Request used to obtain a new access token
 * using a refresh token.
 */
public record RefreshRequest (
        String refreshToken
){}
