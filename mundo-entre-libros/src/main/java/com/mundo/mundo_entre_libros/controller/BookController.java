package com.mundo.mundo_entre_libros.controller;

import com.mundo.mundo_entre_libros.model.Book;
import com.mundo.mundo_entre_libros.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // Obtener todos los libros
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(service.getAllBooks());
    }

    // Obtener un libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getBookById(id));
    }

    // Crear un libro
    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return new ResponseEntity<>(service.saveBook(book), HttpStatus.CREATED);
    }

    // Actualizar un libro
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id,
                                           @RequestBody Book book) {
        return ResponseEntity.ok(service.updateBook(id, book));
    }

    // Eliminar un libro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
