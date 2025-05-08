package com.spacenews.demo.serviceImpl;

import com.spacenews.demo.DTO.PostDTO;
import com.spacenews.demo.DTO.SourceDTO;
import com.spacenews.demo.exceptions.APIException;
import com.spacenews.demo.exceptions.ResourceNotFoundException;
import com.spacenews.demo.model.Post;
import com.spacenews.demo.model.Source;
import com.spacenews.demo.repositories.PostRepository;
import com.spacenews.demo.repositories.SourceRepository;
import com.spacenews.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final SourceRepository sourceRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostDTO getPostById(Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PostDTO createPost(Long id, PostDTO postDTO) {

        Source source = sourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Source", "id", id));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setSource(source);
        boolean exists = postRepository.existsByLink(post.getLink());

        if(exists) {
            throw new APIException("Post already exists");
        }

        Post savedPost = postRepository.save(post);
        PostDTO savedPostDTO = modelMapper.map(savedPost, PostDTO.class);
        savedPostDTO.setSource(modelMapper.map(source, SourceDTO.class));

        return savedPostDTO;
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO postDTO) {

        Post existingPost  = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        existingPost.setTitle(postDTO.getTitle());
        existingPost.setType(postDTO.getType());
        existingPost.setPublishedAt(postDTO.getPublishedAt());
        existingPost.setLink(postDTO.getLink());
        existingPost.setContent(postDTO.getContent());
        existingPost.setImageUrl(postDTO.getImageUrl());
        existingPost.setCategory(postDTO.getCategory());
        existingPost.setLocObject(postDTO.getLocObject());

        Post updatedPost = postRepository.save(existingPost);

        PostDTO updatedDTO = modelMapper.map(updatedPost, PostDTO.class);
        updatedDTO.setSource(modelMapper.map(existingPost.getSource(), SourceDTO.class));
        return updatedDTO;
    }

    @Override
    public PostDTO deletePost(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public void saveAll(List<Post> newPosts) {
        List<Post> filtered = newPosts.stream()
                .filter(post -> !postRepository.existsByLink(post.getLink())).toList();

        System.out.println("New Unique Posts: " + filtered.size());


        postRepository.saveAll(filtered);
    }


}
