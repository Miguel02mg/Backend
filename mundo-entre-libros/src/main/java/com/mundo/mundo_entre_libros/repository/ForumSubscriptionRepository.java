package com.mundo.mundo_entre_libros.repository;

import com.mundo.mundo_entre_libros.model.ForumSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ForumSubscriptionRepository
        extends JpaRepository<ForumSubscription,Integer> {

    Optional<ForumSubscription> findByUser_IdUserAndForum_IdForum(
            Integer userId,
            Integer forumId
    );

    List<ForumSubscription> findByUser_IdUser(Integer userId);

    Integer countByForum_IdForum(Integer forumId);

}

