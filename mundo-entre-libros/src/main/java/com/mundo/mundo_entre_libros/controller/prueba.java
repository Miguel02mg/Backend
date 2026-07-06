package com.mundo.mundo_entre_libros.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class prueba {

    @GetMapping("/test")
    public String test() {
        return "OK";
    }
}