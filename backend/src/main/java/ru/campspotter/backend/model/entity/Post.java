package ru.campspotter.backend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import ru.campspotter.backend.model.enums.PostStatus;

import java.util.HashSet;
import java.util.Set;

/**
 * Temporary Post entity stub.
 * Will be expanded later.
 */
@Entity
@Table(name = "posts",
       indexes = {
       @Index(name = "idx_post_author", columnList = "author_id"),
       @Index(name = "idx_post_status", columnList = "status")
       }
)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull
    private User author;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 5000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status = PostStatus.PENDING;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Location> locations = new HashSet<>();

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Media> media = new HashSet<>();
    protected Post() {
    }

    public static Post create(String title, String description) {
        Post post = new Post();
        post.title = title;
        post.description = description;
        post.status = PostStatus.PENDING;
        return post;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public PostStatus getStatus() {
        return status;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Set<Media> getMedia() {
        return media;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
