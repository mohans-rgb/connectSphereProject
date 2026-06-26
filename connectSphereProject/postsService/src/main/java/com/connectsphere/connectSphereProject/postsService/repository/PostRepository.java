package com.connectsphere.connectSphereProject.postsService.repository;

import com.connectsphere.connectSphereProject.postsService.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
}
