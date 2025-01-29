package io.unitbean.service;

import io.unitbean.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    public UserDetails loadUserByUsername(String username);

    public User getUserById(Integer id);

    public List<User> getAllUsers();

    public List<User> findUsersByUsername(String username);
}
