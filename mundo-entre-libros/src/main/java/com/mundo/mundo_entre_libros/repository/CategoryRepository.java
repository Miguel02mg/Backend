package com.mundo.mundo_entre_libros.repository;

import com.mundo.mundo_entre_libros.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
