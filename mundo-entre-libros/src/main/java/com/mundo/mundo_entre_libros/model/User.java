package com.mundo.mundo_entre_libros.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String passwordHash;

    private LocalDateTime createdAt = LocalDateTime.now();
}
