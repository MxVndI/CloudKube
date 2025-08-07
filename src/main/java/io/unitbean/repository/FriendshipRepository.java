package io.unitbean.repository;

import io.unitbean.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    Set<Friendship> findAllByFirstUserId(Integer firstUserId);

    Set<Friendship> findAllBySecondUserId(Integer secondUserId);
}
