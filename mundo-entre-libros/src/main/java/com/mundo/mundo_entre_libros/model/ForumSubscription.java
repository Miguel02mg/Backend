package com.mundo.mundo_entre_libros.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "forum_subscriptions",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "user_id",
                        "forum_id"
                }
        )
)
@Getter
@Setter
public class ForumSubscription {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subscription")
    private Integer idSubscription;



    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    @JsonIgnore
    private User user;



    @ManyToOne
    @JoinColumn(
            name = "forum_id",
            nullable = false
    )
    @JsonIgnore
    private Forum forum;



    @Column(name = "joined_at")
    private LocalDateTime joinedAt = LocalDateTime.now();



    private Integer points = 0;

}