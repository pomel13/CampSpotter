package ru.campspotter.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.campspotter.backend.config.JwtProperties;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * Service responsible for handling all JWT related operations.
 * <p>
 * Responsibilities:
 *  - Generating JWT tokens
 *  - Extracting information from tokens
 *  - Validating token authenticity and expiration
 * <p>
 * This service is used during authentication and request filtering.
 */

@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * Returns the signing key used to sign and verify JWT tokens.
     */
    private Key getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());

        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT token for the given user.
     * <p>
     * The token contains:
     *  - subject (username/email)
     *  - issue date
     *  - expiration date
     *
     * @param userDetails authenticated user
     * @return signed JWT token
     */
    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                // User identifier
                .setSubject(userDetails.getUsername())

                // Time when the token was issued
                .setIssuedAt(new Date())

                // Token expiration time (24 hours)
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessToken().getExpiration()))

                // Signing the token
                .signWith(getSigningKey())

                .compact();
    }

    /**
     * Extracts username (subject) from the token.
     */
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validates the token for a specific user.
     * <p>
     * Validation includes:
     *  - checking username match
     *  - checking token expiration
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    /**
     * Checks whether the token has expired.
     */
    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts expiration date from the token.
     */
    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the token using a resolver function.
     *
     * @param token JWT token
     * @param claimsResolver function that extracts a claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    /**
     * Parses the token and returns all claims contained inside it.
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()

                // Verifies token signature
                .setSigningKey(getSigningKey())

                .build()

                .parseClaimsJws(token)

                .getBody();
    }

}
