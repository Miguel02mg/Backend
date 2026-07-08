package com.mundo.mundo_entre_libros.service;

import com.mundo.mundo_entre_libros.dto.ForumResponseDTO;
import com.mundo.mundo_entre_libros.model.Forum;
import com.mundo.mundo_entre_libros.repository.ForumRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumService {

    private final ForumRepository forumRepository;

    public List<ForumResponseDTO> getAllForums() {

        return forumRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();

    }

    public ForumResponseDTO getForumById(Integer id) {

        Forum forum = forumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foro no encontrado"));

        return convertToDTO(forum);

    }

    private ForumResponseDTO convertToDTO(Forum forum) {

        return new ForumResponseDTO(
                forum.getIdForum(),
                forum.getNombre(),
                forum.getDescripcion(),
                forum.getIcono()
        );

    }

}