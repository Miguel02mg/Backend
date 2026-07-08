package com.mundo.mundo_entre_libros.repository;

import com.mundo.mundo_entre_libros.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Integer> {
}