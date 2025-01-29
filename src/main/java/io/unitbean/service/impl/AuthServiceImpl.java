package io.unitbean.service.impl;

import io.unitbean.model.User;
import io.unitbean.model.security.UserDetailsImpl;
import io.unitbean.repository.UserRepository;
import io.unitbean.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetailsImpl loginUser(User user) {
        return userRepository.findByUsername(user.getUsername()).get();
    }

    public String registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User successful registered";
    }
}
