package ru.campspotter.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.campspotter.backend.model.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
