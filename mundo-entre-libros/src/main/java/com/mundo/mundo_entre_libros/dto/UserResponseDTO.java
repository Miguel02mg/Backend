package com.mundo.mundo_entre_libros.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class UserResponseDTO {


    private Integer idUser;

    private String name;

    private String lastName;

    private String email;

    private String phone;

    private LocalDateTime createdAt;


}