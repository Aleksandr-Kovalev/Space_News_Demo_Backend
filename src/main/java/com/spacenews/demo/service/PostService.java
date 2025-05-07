package com.spacenews.demo.service;

import com.spacenews.demo.DTO.PostDTO;
import com.spacenews.demo.model.Post;

import java.util.List;

public interface PostService {

    PostDTO getPostById(Long id);
    PostDTO createPost(Long id, PostDTO postDTO);
    PostDTO updatePost(Long id, PostDTO postDTO);
    PostDTO deletePost(Long id);

    void saveAll(List<Post> newPosts);
}
