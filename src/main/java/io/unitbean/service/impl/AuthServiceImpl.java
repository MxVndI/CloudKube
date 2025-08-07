package io.unitbean.service.impl;

import io.unitbean.model.User;
import io.unitbean.model.security.UserDetailsImpl;
import io.unitbean.repository.UserRepository;
import io.unitbean.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserDetailsImpl> loginUser(User user) {
        Optional<UserDetailsImpl> dbUser = userRepository.findByUsername(user.getUsername());
        if (dbUser.isEmpty()) {
            return Optional.empty();
        }

        if (!passwordEncoder.matches(user.getPassword(), dbUser.get().getPassword())) {
            return Optional.empty();
        }

        return Optional.of(new UserDetailsImpl(
                dbUser.get().getPassword(),
                dbUser.get().getUsername(),
                dbUser.get().getId()
        ));
    }

    public String registerUser(User user) {
        if (loginUser(user).isPresent()) {
            return "Пользователь уже зарегистрирован";
        } else {
            // Кодируем пароль и сохраняем пользователя
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "User successfully registered";
        }
    }
}
