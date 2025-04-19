package com.example.Pruebaf5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Pruebaf5.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
