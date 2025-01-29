package io.unitbean.repository;

import io.unitbean.model.Friendship;
import io.unitbean.model.FriendshipId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendshipRepositoryEmbeded extends JpaRepository<Friendship, FriendshipId> {
    Optional<Friendship> findById(FriendshipId friendshipId);
}
