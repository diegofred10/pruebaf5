package com.example.Pruebaf5.services;

import com.example.Pruebaf5.models.Post;
import com.example.Pruebaf5.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final String UPLOAD_DIR = Paths.get("uploads").toAbsolutePath().toString();

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post save(Post post, MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String imagePath = saveFile(file);
                post.setImagen(imagePath);
            }
            return postRepository.save(post);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    public Post updatePost(Long id, Post postDetails, MultipartFile file) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            existingPost.setNombre(postDetails.getNombre());
            existingPost.setContent(postDetails.getContent());

            try {
                if (file != null && !file.isEmpty()) {
                    String imagePath = saveFile(file);
                    existingPost.setImagen(imagePath);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al actualizar la imagen", e);
            }

            return postRepository.save(existingPost);
        }
        return null;
    }

    public boolean deleteById(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private String saveFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        file.transferTo(filePath.toFile());

        return "/uploads/" + fileName;
    }
}
