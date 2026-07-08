package com.mundo.mundo_entre_libros.service;

import com.mundo.mundo_entre_libros.model.User;
import com.mundo.mundo_entre_libros.repository.UserRepository;
import com.mundo.mundo_entre_libros.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.mundo.mundo_entre_libros.dto.UserResponseDTO;

@Service
public class UserService {


    @Autowired
    private JwtService jwtService;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    /*
     * LOGIN
     *
     * Valida email y contraseña.
     * Si son correctos genera un JWT.
     */
    public String login(String email, String password) {


        Optional<User> userOpt =
                userRepository.findByEmail(email);



        if (userOpt.isEmpty()) {

            throw new RuntimeException(
                    "Usuario no encontrado"
            );

        }



        User user = userOpt.get();



        if (!passwordEncoder.matches(
                password,
                user.getPasswordHash()
        )) {


            throw new RuntimeException(
                    "Contraseña incorrecta"
            );

        }



        return jwtService.generateToken(
                user.getEmail()
        );

    }




    /*
     * Buscar usuario por email.
     * Se utilizará en /api/auth/me
     */
    public User findByEmail(String email) {


        return userRepository.findByEmail(email)

                .orElseThrow(
                        () -> new RuntimeException(
                                "Usuario no encontrado"
                        )
                );

    }
    public UserResponseDTO getUserResponse(String email){


        User user = findByEmail(email);


        return new UserResponseDTO(

                user.getIdUser(),

                user.getName(),

                user.getLastName(),

                user.getEmail(),

                user.getPhone(),

                user.getCreatedAt()

        );
    }
}