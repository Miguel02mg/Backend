package com.mundo.mundo_entre_libros.service;

import com.mundo.mundo_entre_libros.model.Saga;

import java.util.List;

public interface SagaService {

    List<Saga> getAll();

    Saga getById(Integer id);

    Saga save(Saga saga);

    Saga update(Integer id, Saga saga);

    void delete(Integer id);
}