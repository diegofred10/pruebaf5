package com.example.Pruebaf5.controllers;

import com.example.Pruebaf5.models.Post;
import com.example.Pruebaf5.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService postService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.findById(id);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Post> createPost(
        @RequestPart("post") String postJson,
        @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            Post post = objectMapper.readValue(postJson, Post.class);
            Post savedPost = postService.save(post, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Post> updatePost(
        @PathVariable Long id,
        @RequestPart("post") String postJson,
        @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            Post postDetails = objectMapper.readValue(postJson, Post.class);
            Post updatedPost = postService.updatePost(id, postDetails, file);
            return updatedPost != null ? ResponseEntity.ok(updatedPost) : ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        boolean deleted = postService.deleteById(id);
        return deleted ? ResponseEntity.ok("Post eliminado correctamente") : ResponseEntity.notFound().build();
    }
}
