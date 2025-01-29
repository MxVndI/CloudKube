package io.unitbean.service;

import io.unitbean.model.User;
import io.unitbean.model.security.UserDetailsImpl;

public interface AuthService {
    public UserDetailsImpl loginUser(User user);

    public String registerUser(User user);
}
