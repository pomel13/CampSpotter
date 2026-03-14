package ru.campspotter.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.campspotter.backend.model.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
}