package ru.campspotter.backend.model.mapper;

import ru.campspotter.backend.dto.user.UserResponse;
import ru.campspotter.backend.model.entity.User;

public class UserMapper {

    public static UserResponse toDTO(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
