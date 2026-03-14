package ru.campspotter.backend.model.entity;

import jakarta.persistence.*;
import ru.campspotter.backend.model.enums.Role;

import java.time.LocalDateTime;

/**
 * User entity represents an application user.
 * <p>
 * Stores authentication and authorization-related data.
 * Business logic should be handled in service layer.
 */

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    protected User() {
        // Required by JPA. Protected to prevent uncontrolled instantiation.
    }

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
}
