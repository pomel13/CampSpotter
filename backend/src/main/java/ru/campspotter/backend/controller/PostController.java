package ru.campspotter.backend.controller;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.campspotter.backend.dto.post.CreatePostRequest;
import ru.campspotter.backend.model.entity.Post;
import ru.campspotter.backend.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    public final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Post createPost(@RequestBody @Valid CreatePostRequest request) {
        return postService.createPost(request);
    }
}
