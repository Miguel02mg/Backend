package com.mundo.mundo_entre_libros.controller;


import com.mundo.mundo_entre_libros.dto.SubscriptionDTO;
import com.mundo.mundo_entre_libros.model.ForumSubscription;
import com.mundo.mundo_entre_libros.service.ForumSubscriptionService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ForumSubscriptionController {


    private final ForumSubscriptionService subscriptionService;



    // =====================================
    // SUSCRIBIRSE A UN FORO
    // =====================================

    @PostMapping
    public ForumSubscription subscribe(
            @RequestBody SubscriptionDTO dto,
            Authentication authentication
    ){

        return subscriptionService.subscribe(
                dto,
                authentication
        );

    }



    // =====================================
    // CANCELAR SUSCRIPCIÓN
    // =====================================

    @DeleteMapping("/{forumId}")
    public String unsubscribe(
            @PathVariable Integer forumId,
            Authentication authentication
    ){

        subscriptionService.unsubscribe(
                forumId,
                authentication
        );

        return "Suscripción eliminada correctamente";

    }



    // =====================================
    // COMPROBAR SI ESTÁ SUSCRITO
    // =====================================

    @GetMapping("/check/{forumId}")
    public boolean checkSubscription(
            @PathVariable Integer forumId,
            Authentication authentication
    ){

        return subscriptionService.isSubscribed(
                forumId,
                authentication
        );

    }



    // =====================================
    // OBTENER PUNTOS
    // =====================================

    @GetMapping("/points/{forumId}")
    public Integer getPoints(
            @PathVariable Integer forumId,
            Authentication authentication
    ){

        return subscriptionService.getPoints(
                forumId,
                authentication
        );

    }



    // =====================================
    // ACTUALIZAR PUNTOS
    // =====================================

    @PutMapping("/points")
    public ForumSubscription updatePoints(
            @RequestBody PointsRequest request,
            Authentication authentication
    ){

        return subscriptionService.updatePoints(
                request.forumId(),
                request.points(),
                authentication
        );

    }



    // =====================================
    // CONTAR MIEMBROS
    // =====================================

    @GetMapping("/members/{forumId}")
    public Integer countMembers(
            @PathVariable Integer forumId
    ){

        return subscriptionService.countMembers(forumId);

    }



    public record PointsRequest(

            Integer forumId,

            Integer points

    ){}


}