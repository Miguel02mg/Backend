package com.mundo.mundo_entre_libros.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Service
public class JwtService {


    @Value("${jwt.secret}")
    private String secret;



    private SecretKey getKey(){

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

    }



    private static final long EXPIRATION =
            1000 * 60 * 60 * 24;



    public String generateToken(String email){


        return Jwts.builder()

                .subject(email)

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION
                        )
                )

                .signWith(getKey())

                .compact();

    }




    public String extractEmail(String token){


        Claims claims =
                Jwts.parser()

                        .verifyWith(getKey())

                        .build()

                        .parseSignedClaims(token)

                        .getPayload();



        return claims.getSubject();

    }




    public boolean isTokenValid(String token){


        try{

            extractEmail(token);

            return true;


        }catch(Exception e){

            return false;

        }

    }


}