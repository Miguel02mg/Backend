package com.mundo.mundo_entre_libros.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post")
    private Integer idPost;



    // ==========================
    // FORO AL QUE PERTENECE
    // ==========================

    @ManyToOne
    @JoinColumn(name = "forum_id", nullable = false)
    private Forum forum;



    // ==========================
    // USUARIO QUE PUBLICÓ
    // ==========================

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    @Column(name = "title_post", nullable = false)
    private String titlePost;



    @Column(columnDefinition = "TEXT")
    private String content;



    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();



    // Comentarios del post

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

}