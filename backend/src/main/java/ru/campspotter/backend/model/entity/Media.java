package ru.campspotter.backend.model.entity;

import jakarta.persistence.*;
import ru.campspotter.backend.model.enums.MediaStatus;
import ru.campspotter.backend.model.enums.MediaType;

/**
 * Media entity represents uploaded media files (photos or videos)
 * that belong to a specific post.
 * <p>
 * Stores only metadata and access URL.
 * Physical files are stored in cloud object storage (S3-compatible).
 */
@Entity
@Table(
        name = "media",
        indexes = {
                @Index(name = "idx_media_post", columnList = "post_id"),
                @Index(name = "idx_media_post_order", columnList = "post_id, orderIndex")
        }
)
public class Media extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Public or signed URL to access media file in cloud storage.
     */
    @Column(nullable = false, length = 1024)
    private String url;

    /**
     * Type of media: PHOTO or VIDEO.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MediaType type;

    /**
     * Moderation status of media.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MediaStatus status = MediaStatus.PENDING;

    /**
     * Order of media inside post.
     * Used for correct display sequence.
     */
    @Column(nullable = false)
    private Integer orderIndex = 0;

    /**
     * Marks media as main (cover) for post.
     * Optional.
     */
    @Column(nullable = false)
    private Boolean cover = false; // Business rule: only one media per post can be marked as cover

    /**
     *  Size of file in bytes.
     */
    @Column(nullable = false)
    private Long fileSize;

    /**
     * MIME type of media (e.g. image/jpeg, video/mp4).
     */
    @Column(nullable = false)
    private String mimeType;

    /**
     * Media width in pixels (for images and videos).
     */
    private Integer width;

    /**
     * Media height in pixels (for images and videos).
     */
    private Integer height;

    /**
     * Duration in seconds (only for videos).
     */
    private Integer duration;

    /**
     * Post to which this media belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    protected Media() {
        // Required by JPA
    }

    public Media(
            String url,
            MediaType type,
            Long fileSize,
            String mimeType,
            Post post
    ) {
        this.url = url;
        this.type = type;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.post = post;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public MediaType getType() {
        return type;
    }

    public MediaStatus getStatus() {
        return status;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public boolean isCover() {
        return cover;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getDuration() {
        return duration;
    }

    public Post getPost() {
        return post;
    }
}
