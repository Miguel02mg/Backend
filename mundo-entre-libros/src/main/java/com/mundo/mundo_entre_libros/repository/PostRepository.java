package com.mundo.mundo_entre_libros.repository;

import com.mundo.mundo_entre_libros.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {


    List<Post> findByForum_IdForum(Integer forumId);

}