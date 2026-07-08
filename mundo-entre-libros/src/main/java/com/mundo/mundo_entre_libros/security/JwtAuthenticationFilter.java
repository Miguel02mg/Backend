package com.mundo.mundo_entre_libros.security;

import com.mundo.mundo_entre_libros.model.User;
import com.mundo.mundo_entre_libros.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtService jwtService;


    @Autowired
    private UserRepository userRepository;




    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {



        String header =
                request.getHeader("Authorization");



        if(header == null || !header.startsWith("Bearer ")){

            filterChain.doFilter(request,response);
            return;

        }



        String token =
                header.substring(7);



        if(jwtService.isTokenValid(token)){


            String email =
                    jwtService.extractEmail(token);



            User user =
                    userRepository.findByEmail(email)
                            .orElse(null);



            if(user != null){


                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(

                                user,

                                null,

                                List.of(
                                        new SimpleGrantedAuthority("USER")
                                )

                        );



                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

            }

        }



        filterChain.doFilter(request,response);

    }

}