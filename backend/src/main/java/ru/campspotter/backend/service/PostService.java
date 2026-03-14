package ru.campspotter.backend.service;

import org.springframework.stereotype.Service;
import ru.campspotter.backend.model.dto.post.CreatePostRequest;
import ru.campspotter.backend.model.entity.Post;
import ru.campspotter.backend.model.entity.User;
import ru.campspotter.backend.model.enums.PostStatus;
import ru.campspotter.backend.repository.PostRepository;
import ru.campspotter.backend.repository.UserRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(CreatePostRequest request) {
        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = Post.create(request.getTitle(), request.getDescription());
        post.setAuthor(author);
        post.setStatus(PostStatus.PENDING);

        return postRepository.save(post);
    }
}
