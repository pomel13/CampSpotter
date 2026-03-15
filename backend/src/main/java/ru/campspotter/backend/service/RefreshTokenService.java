package ru.campspotter.backend.service;

import org.springframework.stereotype.Service;
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

    /**
     * RefreshToken lifetime (7 days).
     */
    private final long refreshTokenDuration = 7 * 24 * 60 * 60;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    /**
     * Creates a new refresh token for the user.
     */
    public RefreshToken createRefreshToken(User user) {

        String tokenValue = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken(
                tokenValue,
                user,
                Instant.now().plusSeconds(refreshTokenDuration)
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
