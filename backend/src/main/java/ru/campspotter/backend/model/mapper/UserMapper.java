package ru.campspotter.backend.model.mapper;

import ru.campspotter.backend.model.dto.user.UserResponseDTO;
import ru.campspotter.backend.model.entity.User;

public class UserMapper {

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
