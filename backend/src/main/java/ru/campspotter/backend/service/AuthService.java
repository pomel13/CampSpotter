package ru.campspotter.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.campspotter.backend.dto.auth.AuthResponse;
import ru.campspotter.backend.dto.auth.LoginRequest;
import ru.campspotter.backend.dto.auth.RefreshRequest;
import ru.campspotter.backend.dto.auth.RegisterRequest;
import ru.campspotter.backend.dto.user.UserResponse;
import ru.campspotter.backend.model.entity.RefreshToken;
import ru.campspotter.backend.model.entity.User;
import ru.campspotter.backend.model.mapper.UserMapper;
import ru.campspotter.backend.repository.RefreshTokenRepository;
import ru.campspotter.backend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.campspotter.backend.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    public final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository,
                       RefreshTokenRepository refreshTokenRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       RefreshTokenService refreshTokenService)
    {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getEmail(),
                hashedPassword,
                request.getUsername()
        );

        User savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }

    public AuthResponse login(LoginRequest request) {

        // Authenticate user (Spring Security validates password)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Load user from database
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate access JWT token
        String accessToken = jwtService.generateToken(user);

        // Generate refresh token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        // Return token to client
        return new AuthResponse(
                accessToken,
                refreshToken.getToken());
    }

    public AuthResponse refreshToken(RefreshRequest request) {

        String requestRefreshToken = request.refreshToken();

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(requestRefreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        refreshTokenService.verifyExpiration(refreshToken);

        User user = refreshToken.getUser();

        String newAccessToken = jwtService.generateToken(user);

        return new AuthResponse(
                newAccessToken,
                requestRefreshToken
        );
    }
}
