package com.spacenews.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "posts", indexes = {
        @Index(name = "idx_published_at", columnList = "published_at")
})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Post type is required")
    private PostType type;

    private String title;

    private LocalDateTime publishedAt;
    private LocalDateTime fetchedAt;

    private String link;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String category;
    private String locObject;

}
