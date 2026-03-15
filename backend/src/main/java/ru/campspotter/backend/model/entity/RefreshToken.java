package ru.campspotter.backend.model.entity;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * Entity representing refresh tokens stored in the database.
 * <p>
 * Refresh tokens allow clients to obtain a new access token
 * without requiring the user login again.
 */
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Refresh token value (random UUID string).
     */
    @Column(nullable = false, unique = true)
    private String token;

    /**
     * Token owner.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Expiration timestamp.
     */
    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    public RefreshToken () {}

    public RefreshToken(String token, User user, Instant expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public boolean isExpired() {
        return expiryDate.isBefore(Instant.now());
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }
}
