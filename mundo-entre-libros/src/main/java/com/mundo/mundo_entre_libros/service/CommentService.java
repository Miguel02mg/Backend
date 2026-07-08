package com.mundo.mundo_entre_libros.service;

import com.mundo.mundo_entre_libros.dto.CommentDTO;
import com.mundo.mundo_entre_libros.dto.CommentResponseDTO;
import com.mundo.mundo_entre_libros.dto.UpdateCommentDTO;
import com.mundo.mundo_entre_libros.model.Comment;
import com.mundo.mundo_entre_libros.model.Post;
import com.mundo.mundo_entre_libros.model.User;
import com.mundo.mundo_entre_libros.repository.CommentRepository;
import com.mundo.mundo_entre_libros.repository.PostRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {


    private final CommentRepository commentRepository;

    private final PostRepository postRepository;



    // =====================================
    // CREAR COMENTARIO
    // =====================================

    public CommentResponseDTO create(
            CommentDTO dto,
            Authentication authentication
    ){


        Post post = postRepository.findById(dto.postId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Post no encontrado"
                        )
                );



        // Usuario obtenido desde JWT
        User user = (User) authentication.getPrincipal();



        Comment comment = new Comment();


        comment.setPost(post);

        comment.setUser(user);

        comment.setContent(dto.content());



        Comment savedComment =
                commentRepository.save(comment);



        return convertToDTO(savedComment);

    }





    // =====================================
    // OBTENER COMENTARIOS DE UN POST
    // =====================================

    public List<CommentResponseDTO> getByPost(
            Integer postId
    ){


        return commentRepository
                .findByPost_IdPost(postId)
                .stream()
                .map(this::convertToDTO)
                .toList();

    }





    // =====================================
    // CONVERTIR A DTO
    // =====================================

    private CommentResponseDTO convertToDTO(
            Comment comment
    ){


        return new CommentResponseDTO(

                comment.getIdComment(),

                comment.getPost().getIdPost(),

                comment.getUser().getIdUser(),

                comment.getContent(),

                comment.getUser().getName(),

                comment.getCreatedAt()

        );

    }





    // =====================================
    // ACTUALIZAR COMENTARIO
    // =====================================

    public CommentResponseDTO update(
            Integer id,
            UpdateCommentDTO dto
    ){


        Comment comment =
                commentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Comentario no encontrado"
                                )
                        );



        comment.setContent(dto.content());



        Comment updatedComment =
                commentRepository.save(comment);



        return convertToDTO(updatedComment);

    }





    // =====================================
    // ELIMINAR COMENTARIO
    // =====================================

    public void delete(
            Integer id
    ){

        commentRepository.deleteById(id);

    }

}