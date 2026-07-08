package com.mundo.mundo_entre_libros.controller;

import com.mundo.mundo_entre_libros.dto.ForumResponseDTO;
import com.mundo.mundo_entre_libros.service.ForumService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forums")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ForumController {

    private final ForumService forumService;

    @GetMapping
    public List<ForumResponseDTO> getAllForums() {
        return forumService.getAllForums();
    }

    @GetMapping("/{id}")
    public ForumResponseDTO getForumById(@PathVariable Integer id) {
        return forumService.getForumById(id);
    }

}