package com.spacenews.demo.repositories;

import com.spacenews.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{
    public Post findBylink(String url);

    boolean existsByLink(String link);
}
