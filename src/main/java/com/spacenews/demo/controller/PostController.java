package com.spacenews.demo.controller;

import com.spacenews.demo.DTO.PostDTO;
import com.spacenews.demo.Response.CustomResponse;
import com.spacenews.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        PostDTO postDTO = postService.getPostById(id);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping("/post/new/{sourceId}")
    public ResponseEntity<PostDTO> addPost(@PathVariable Long sourceId, @RequestBody PostDTO postDTO) {
        PostDTO savedPostDTO = postService.createPost(sourceId, postDTO);
        return new ResponseEntity<>(savedPostDTO, HttpStatus.CREATED);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<CustomResponse<PostDTO>> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        PostDTO updatedPostDTO = postService.updatePost(id, postDTO);
        CustomResponse<PostDTO> response = new CustomResponse<>("Successfully updated post", updatedPostDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<CustomResponse<PostDTO>> deletePost(@PathVariable Long id) {
        PostDTO deletedPostDTO = postService.deletePost(id);
        CustomResponse<PostDTO> response = new CustomResponse<>("Successfully deleted post", deletedPostDTO);
        return ResponseEntity.ok(response);
    }
}
