package com.mundo.mundo_entre_libros.service.impl;

import com.mundo.mundo_entre_libros.exception.ResourceNotFoundException;
import com.mundo.mundo_entre_libros.model.Book;
import com.mundo.mundo_entre_libros.repository.BookRepository;
import com.mundo.mundo_entre_libros.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @Override
    public Book getBookById(Integer id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Libro con id " + id + " no encontrado."));
    }

    @Override
    public Book saveBook(Book book) {
        return repository.save(book);
    }

    @Override
    public Book updateBook(Integer id, Book book) {

        Book existing = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Libro con id " + id + " no encontrado."));

        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setEdition(book.getEdition());
        existing.setISBN(book.getISBN());
        existing.setPrice(book.getPrice());
        existing.setCoverUrl(book.getCoverUrl());
        existing.setSynopsis(book.getSynopsis());
        existing.setCategory(book.getCategory());
        existing.setSaga(book.getSaga());

        return repository.save(existing);
    }

    @Override
    public void deleteBook(Integer id) {
        Book existing = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Libro con id " + id + " no encontrado."));
        repository.delete(existing);
    }
}
