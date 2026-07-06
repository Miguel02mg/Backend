package com.mundo.mundo_entre_libros.service.impl;

import com.mundo.mundo_entre_libros.model.Saga;
import com.mundo.mundo_entre_libros.repository.SagaRepository;
import com.mundo.mundo_entre_libros.service.SagaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SagaServiceImpl implements SagaService {

    private final SagaRepository repository;

    public SagaServiceImpl(SagaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Saga> getAll() {
        return repository.findAll();
    }

    @Override
    public Saga getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Saga no encontrada"));
    }

    @Override
    public Saga save(Saga saga) {
        return repository.save(saga);
    }

    @Override
    public Saga update(Integer id, Saga saga) {
        Saga existing = getById(id);

        existing.setName(saga.getName());
        existing.setAuthor(saga.getAuthor());
        existing.setEditorial(saga.getEditorial());
        existing.setPrice(saga.getPrice());
        existing.setCoverUrl(saga.getCoverUrl());
        existing.setDescription(saga.getDescription());
        existing.setISBN(saga.getISBN());

        return repository.save(existing);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}