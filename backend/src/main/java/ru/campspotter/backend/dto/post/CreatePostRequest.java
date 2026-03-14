package ru.campspotter.backend.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePostRequest {

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    @Size(max = 5000)
    private String description;

    @NotNull
    private Long authorId;

    public @NotBlank @Size(max = 200) String getTitle() {
        return title;
    }

    public @NotBlank @Size(max = 5000) String getDescription() {
        return description;
    }

    public @NotNull Long getAuthorId() {
        return authorId;
    }
}