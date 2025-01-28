package io.unitbean.service;

import io.unitbean.model.security.UserDetailsImpl;
import io.unitbean.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetailsImpl> userDetails = userRepository.findByUsername(username);
        return userDetails.orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}
