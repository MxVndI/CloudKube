package io.unitbean.service;

import io.unitbean.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    UserDetails loadUserByUsername(String username);

    User getUserById(Integer id);

    List<User> getAllUsers();

    List<User> getUsersByUsername(String username);

    String getUserImageName(Integer userId);
}
