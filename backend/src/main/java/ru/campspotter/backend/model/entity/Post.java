package ru.campspotter.backend.model.entity;

import jakarta.persistence.*;

/**
 * Temporary Post entity stub.
 * Will be expanded later.
 */
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected Post() {
    }

    public Long getId() {
        return id;
    }
}
