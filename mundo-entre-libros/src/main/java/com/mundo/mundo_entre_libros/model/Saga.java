package com.mundo.mundo_entre_libros.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sagas")
public class Saga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSaga;

    private String name;

    private String author;

    private String editorial;

    private Double price;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "ISBN")
    private String ISBN;
}