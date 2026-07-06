package com.mundo.mundo_entre_libros.repository;

import com.mundo.mundo_entre_libros.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}