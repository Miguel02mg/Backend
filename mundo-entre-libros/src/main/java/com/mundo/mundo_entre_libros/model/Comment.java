package com.mundo.mundo_entre_libros.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    private Integer idComment;



    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;



    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    @Column(columnDefinition = "TEXT")
    private String content;



    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}