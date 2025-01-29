package io.unitbean.repository;

import io.unitbean.model.User;
import io.unitbean.model.security.UserDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<UserDetailsImpl> findByUsername(String username);

    Optional<User> findById(Integer id);

    List<User> findByUsernameContainingIgnoreCase(String username);
}
