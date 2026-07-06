package com.mundo.mundo_entre_libros.repository;

import com.mundo.mundo_entre_libros.model.Saga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SagaRepository extends JpaRepository<Saga, Integer> {
}