package com.mundo.mundo_entre_libros.controller;

import com.mundo.mundo_entre_libros.model.User;
import com.mundo.mundo_entre_libros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    public User createUser(@RequestBody User user) {

        // 1. Validar email duplicado
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }


        // 2. Validar contraseña
        validatePassword(user.getPassword());
        System.out.println("PASSWORD RECIBIDA: " + user.getPassword());
        // 3. Encriptar contraseña
        String encrypted = passwordEncoder.encode(user.getPassword());
        user.setPasswordHash(encrypted);

        return userRepository.save(user);
    }

    // VALIDACIÓN DE CONTRASEÑA
    private void validatePassword(String password) {

        if (password == null || password.length() < 8) {
            throw new RuntimeException("Mínimo 8 caracteres");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new RuntimeException("Debe tener al menos una mayúscula");
        }

        if (!password.matches(".*[0-9].*")) {
            throw new RuntimeException("Debe tener al menos un número");
        }

        if (!password.matches(".*[!@#$%^&*()._\\-].*")) {
            throw new RuntimeException("Debe tener un símbolo");
        }
    }
}