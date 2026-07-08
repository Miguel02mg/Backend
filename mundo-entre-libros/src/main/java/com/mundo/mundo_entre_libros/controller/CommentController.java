package com.mundo.mundo_entre_libros.controller;


import com.mundo.mundo_entre_libros.dto.CommentDTO;
import com.mundo.mundo_entre_libros.dto.CommentResponseDTO;
import com.mundo.mundo_entre_libros.dto.UpdateCommentDTO;
import com.mundo.mundo_entre_libros.service.CommentService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CommentController {


    private final CommentService commentService;



    @PostMapping
    public CommentResponseDTO create(
            @RequestBody CommentDTO dto,
            Authentication authentication
    ){

        return commentService.create(
                dto,
                authentication
        );

    }



    @GetMapping("/post/{postId}")
    public List<CommentResponseDTO> getByPost(
            @PathVariable Integer postId
    ){

        return commentService.getByPost(postId);

    }



    @PutMapping("/{id}")
    public CommentResponseDTO update(
            @PathVariable Integer id,
            @RequestBody UpdateCommentDTO dto
    ){

        return commentService.update(id, dto);

    }



    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Integer id
    ){

        commentService.delete(id);

    }

}