package io.unitbean.service;

import io.unitbean.model.User;
import io.unitbean.model.security.UserDetailsImpl;

public interface AuthService {

    UserDetailsImpl loginUser(User user);

    String registerUser(User user);
}
