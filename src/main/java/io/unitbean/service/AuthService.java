package io.unitbean.service;

import io.unitbean.model.User;
import io.unitbean.model.security.UserDetailsImpl;

import java.util.Optional;

public interface AuthService {
    Optional<UserDetailsImpl> loginUser(User user);

    String registerUser(User user);
}
