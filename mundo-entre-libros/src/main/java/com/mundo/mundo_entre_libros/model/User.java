package com.mundo.mundo_entre_libros.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;


    private String name;

    private String lastName;

    private String email;

    private String phone;


    @Column(name = "password_hash")
    private String passwordHash;


    // SOLO PARA RECIBIR PASSWORD DEL FRONT
    @Transient
    private String password;


    private LocalDateTime createdAt = LocalDateTime.now();



    // ==========================
    // RELACIONES FORO
    // ==========================


    @OneToMany(mappedBy = "user")
    private List<Post> posts;


    @OneToMany(mappedBy = "user")
    private List<Comment> comments;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ForumSubscription> subscriptions;

}