package com.mundo.mundo_entre_libros.service;

import com.mundo.mundo_entre_libros.dto.PostDTO;
import com.mundo.mundo_entre_libros.dto.PostResponseDTO;
import com.mundo.mundo_entre_libros.model.Forum;
import com.mundo.mundo_entre_libros.model.Post;
import com.mundo.mundo_entre_libros.model.User;
import com.mundo.mundo_entre_libros.repository.ForumRepository;
import com.mundo.mundo_entre_libros.repository.PostRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;

    private final ForumRepository forumRepository;



    // =====================================
    // CREAR POST
    // =====================================

    public PostResponseDTO create(
            PostDTO dto,
            Authentication authentication
    ){


        // Obtenemos el usuario desde JWT
        User user = (User) authentication.getPrincipal();



        Forum forum = forumRepository.findById(dto.forumId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Foro no encontrado"
                        )
                );



        Post post = new Post();


        post.setForum(forum);

        post.setUser(user);

        post.setTitlePost(dto.titlePost());

        post.setContent(dto.content());



        Post savedPost = postRepository.save(post);



        return convertToDTO(savedPost);

    }





    // =====================================
    // OBTENER POSTS DE UN FORO
    // =====================================

    public List<PostResponseDTO> getByForum(
            Integer forumId
    ){

        return postRepository
                .findByForum_IdForum(forumId)
                .stream()
                .map(this::convertToDTO)
                .toList();

    }





    // =====================================
    // ELIMINAR POST
    // =====================================

    public void delete(Integer id){

        postRepository.deleteById(id);

    }





    // =====================================
    // CONVERTIR A DTO
    // =====================================

    private PostResponseDTO convertToDTO(Post post){


        return new PostResponseDTO(

                post.getIdPost(),

                post.getForum().getIdForum(),

                post.getUser().getIdUser(),

                post.getTitlePost(),

                post.getContent(),

                post.getUser().getName(),

                post.getCreatedAt()

        );

    }

}