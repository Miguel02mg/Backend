package com.mundo.mundo_entre_libros.controller;

import com.mundo.mundo_entre_libros.dto.UserResponseDTO;
import com.mundo.mundo_entre_libros.security.JwtService;
import com.mundo.mundo_entre_libros.model.User;
import com.mundo.mundo_entre_libros.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {


    @Autowired
    private UserService userService;


    @Autowired
    private JwtService jwtService;



    @PostMapping("/login")
    public Map<String,String> login(
            @RequestBody Map<String,String> request){


        String token = userService.login(
                request.get("email"),
                request.get("password")
        );


        return Map.of(
                "token", token
        );
    }

    @GetMapping("/me")
    public UserResponseDTO me(Authentication authentication){


        User user = (User) authentication.getPrincipal();


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

