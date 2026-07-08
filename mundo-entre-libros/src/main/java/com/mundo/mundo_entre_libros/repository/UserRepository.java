package com.mundo.mundo_entre_libros.repository;

import com.mundo.mundo_entre_libros.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}