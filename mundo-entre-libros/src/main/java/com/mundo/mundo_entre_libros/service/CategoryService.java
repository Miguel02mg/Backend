package com.mundo.mundo_entre_libros.service;

import com.mundo.mundo_entre_libros.model.Category;
import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category getById(Integer id);

    Category save(Category category);

    Category update(Integer id, Category category);

    void delete(Integer id);
}