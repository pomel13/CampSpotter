package ru.campspotter.backend.model.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.campspotter.backend.model.enums.Role;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Represents an application user in the system.
 *
 * <p>This entity maps to the 'users' table and stores all user-related data
 * including authentication credentials and role information. It implements
 * Spring Security's {@link UserDetails} interface to integrate with the
 * security framework.
 *
 * <p>Passwords are stored in BCrypt-encoded format. The class follows JPA
 * specifications with a protected no-args constructor.
 *
 * @see UserDetails
 * @see Role
 */
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User's email address used as a primary login identifier.
     * Must be unique across the system.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Stores hashed password (BCrypt).
     * Raw passwords must never be persisted.
     */
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    /**
     * User's role defining system permissions.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    protected User() {
        // Required by JPA. Protected to prevent uncontrolled instantiation.
    }

    /**
     * Creates a new user with the specified credentials.
     *
     * @param email user's email address
     * @param password BCrypt-encoded password
     * @param username display name
     */
    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = Role.USER;
    }


    // Getters:


    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // ================ UserDetails Implementation ================

    /**
     * Returns the authorities granted to the user.
     * Converts the user's role to Spring Security's authority format.
     *
     * @return collection containing the user's role prefixed with "ROLE_"
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return true - accounts never expire in this implementation
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return true - accounts never locked in this implementation
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     *
     * @return true - credentials never expire in this implementation
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return true - accounts always enabled in this implementation
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
