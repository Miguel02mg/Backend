package com.mundo.mundo_entre_libros.repository;

import com.mundo.mundo_entre_libros.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer>{

    List<Comment> findByPost_IdPost(Integer postId);

}