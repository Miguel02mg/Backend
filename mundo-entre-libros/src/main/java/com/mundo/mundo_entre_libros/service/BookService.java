package com.mundo.mundo_entre_libros.service;

import com.mundo.mundo_entre_libros.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Integer id);

    Book saveBook(Book book);

    Book updateBook(Integer id, Book book);

    void deleteBook(Integer id);

}