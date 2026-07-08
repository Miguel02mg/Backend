package com.mundo.mundo_entre_libros.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "forums")
@Getter
@Setter
public class Forum {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_forum")
    private Integer idForum;


    private String nombre;


    private String descripcion;


    private String icono;



    // ==========================
    // RELACIONES
    // ==========================


    // Un foro tiene muchos posts
    @OneToMany(mappedBy = "forum")
    private List<Post> posts;



    // Usuarios suscritos al foro
    @OneToMany(mappedBy = "forum")
    @JsonIgnore
    private List<ForumSubscription> subscriptions;

}