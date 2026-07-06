package com.mundo.mundo_entre_libros.service.impl;

import com.mundo.mundo_entre_libros.model.Category;
import com.mundo.mundo_entre_libros.repository.CategoryRepository;
import com.mundo.mundo_entre_libros.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }

    @Override
    public Category update(Integer id, Category category) {
        Category existing = getById(id);
        existing.setName(category.getName());
        return repository.save(existing);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}