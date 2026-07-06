package com.mundo.mundo_entre_libros.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBook;

    private String title;

    private String author;

    private String edition;

    @Column(name = "ISBN")
    private String ISBN;

    private Double price;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    // RELACIÓN: CATEGORY (Muchos libros → 1 categoría)
    @ManyToOne
    @JoinColumn(name = "categories_id_category")
    private Category category;

    // RELACIÓN: SAGA (Muchos libros → 1 saga, puede ser null)
    @ManyToOne
    @JoinColumn(name = "sagas_id_saga")
    private Saga saga;
}