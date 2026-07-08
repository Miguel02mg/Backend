package com.mundo.mundo_entre_libros.service;

import com.mundo.mundo_entre_libros.dto.SubscriptionDTO;
import com.mundo.mundo_entre_libros.model.Forum;
import com.mundo.mundo_entre_libros.model.ForumSubscription;
import com.mundo.mundo_entre_libros.model.User;
import com.mundo.mundo_entre_libros.repository.ForumRepository;
import com.mundo.mundo_entre_libros.repository.ForumSubscriptionRepository;
import com.mundo.mundo_entre_libros.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ForumSubscriptionService {


    private final ForumSubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ForumRepository forumRepository;



    // =====================================
    // CREAR SUSCRIPCIÓN
    // =====================================

    public ForumSubscription subscribe(
            SubscriptionDTO dto,
            Authentication authentication
    ){

        User user = getUser(authentication);



        Forum forum = forumRepository.findById(dto.forumId())
                .orElseThrow(() ->
                        new RuntimeException("Foro no encontrado")
                );



        boolean exists =
                subscriptionRepository
                        .findByUser_IdUserAndForum_IdForum(
                                user.getIdUser(),
                                dto.forumId()
                        )
                        .isPresent();



        if(exists){

            throw new RuntimeException(
                    "El usuario ya está suscrito a este foro"
            );

        }



        ForumSubscription subscription =
                new ForumSubscription();


        subscription.setUser(user);
        subscription.setForum(forum);
        subscription.setPoints(0);



        return subscriptionRepository.save(subscription);

    }





    // =====================================
    // CANCELAR SUSCRIPCIÓN
    // =====================================

    public void unsubscribe(
            Integer forumId,
            Authentication authentication
    ){

        User user = getUser(authentication);



        ForumSubscription subscription =
                subscriptionRepository
                        .findByUser_IdUserAndForum_IdForum(
                                user.getIdUser(),
                                forumId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Suscripción no encontrada"
                                )
                        );



        subscriptionRepository.delete(subscription);

    }





    // =====================================
    // SABER SI ESTA SUSCRITO
    // =====================================

    public boolean isSubscribed(
            Integer forumId,
            Authentication authentication
    ){

        User user = getUser(authentication);



        return subscriptionRepository
                .findByUser_IdUserAndForum_IdForum(
                        user.getIdUser(),
                        forumId
                )
                .isPresent();

    }





    // =====================================
    // OBTENER FOROS DEL USUARIO
    // =====================================

    public List<ForumSubscription> getUserSubscriptions(
            Authentication authentication
    ){

        User user = getUser(authentication);



        return subscriptionRepository
                .findByUser_IdUser(
                        user.getIdUser()
                );

    }





    // =====================================
    // CONTAR MIEMBROS DE UN FORO
    // =====================================

    public Integer countMembers(
            Integer forumId
    ){

        return subscriptionRepository
                .countByForum_IdForum(forumId);

    }





    // =====================================
    // OBTENER PUNTOS
    // =====================================

    public Integer getPoints(
            Integer forumId,
            Authentication authentication
    ){

        User user = getUser(authentication);



        ForumSubscription subscription =
                subscriptionRepository
                        .findByUser_IdUserAndForum_IdForum(
                                user.getIdUser(),
                                forumId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "No existe suscripción"
                                )
                        );



        return subscription.getPoints();

    }





    // =====================================
    // SUMAR / RESTAR PUNTOS
    // =====================================

    public ForumSubscription updatePoints(
            Integer forumId,
            Integer points,
            Authentication authentication
    ){

        User user = getUser(authentication);



        ForumSubscription subscription =
                subscriptionRepository
                        .findByUser_IdUserAndForum_IdForum(
                                user.getIdUser(),
                                forumId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Usuario no suscrito"
                                )
                        );



        int current =
                subscription.getPoints() == null
                        ? 0
                        : subscription.getPoints();



        int updated =
                Math.max(
                        0,
                        current + points
                );



        subscription.setPoints(updated);



        return subscriptionRepository.save(subscription);

    }





    // =====================================
    // OBTENER USUARIO DESDE JWT
    // =====================================

    private User getUser(
            Authentication authentication
    ){

        if(authentication == null ||
                authentication.getPrincipal() == null){

            throw new RuntimeException(
                    "Usuario no autenticado"
            );
        }



        return (User) authentication.getPrincipal();

    }

}