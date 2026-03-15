package ru.campspotter.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.campspotter.backend.config.JwtProperties;
import ru.campspotter.backend.model.entity.RefreshToken;
import ru.campspotter.backend.model.entity.User;
import ru.campspotter.backend.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.UUID;

/**
 * Service responsible for creating and validating refresh tokens.
 */
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtProperties jwtProperties) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtProperties = jwtProperties;
    }

    /**
     * Creates a new refresh token for the user.
     */
    public RefreshToken createRefreshToken(User user) {

        String tokenValue = UUID.randomUUID().toString();

        long refreshTokenDurationMs =
                jwtProperties.getRefreshToken().getExpiration();

        RefreshToken refreshToken = new RefreshToken(
                tokenValue,
                user,
                Instant.now().plusMillis(refreshTokenDurationMs)
        );

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Validates refresh token expiration.
     */
    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }

        return token;
    }
}
