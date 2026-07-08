package com.mundo.mundo_entre_libros.controller;


import com.mundo.mundo_entre_libros.dto.PostDTO;
import com.mundo.mundo_entre_libros.dto.PostResponseDTO;
import com.mundo.mundo_entre_libros.service.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostController {


    private final PostService postService;



    @PostMapping
    public PostResponseDTO create(
            @RequestBody PostDTO dto,
            Authentication authentication
    ){

        return postService.create(
                dto,
                authentication
        );

    }



    @GetMapping("/forum/{forumId}")
    public List<PostResponseDTO> getByForum(
            @PathVariable Integer forumId
    ){

        return postService.getByForum(forumId);

    }



    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Integer id
    ){

        postService.delete(id);

    }

}