package ru.campspotter.backend.dto.user;

import ru.campspotter.backend.model.enums.Role;

import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String email;
    private String username;
    private Role role;
    private LocalDateTime createdAt;

    public UserResponse(Long id, String email, String username, Role role, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
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
}
