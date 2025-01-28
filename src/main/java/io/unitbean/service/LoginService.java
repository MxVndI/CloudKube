package io.unitbean.service;

import io.unitbean.model.User;
import io.unitbean.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    public User loginUser(User user) {
        return user;
    }
}
