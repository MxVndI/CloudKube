package io.unitbean.service;

import io.unitbean.model.User;
import io.unitbean.model.security.UserDetailsImpl;
import io.unitbean.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetailsImpl> userDetails = userRepository.findByUsername(username);
        return userDetails.orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findUsersByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }
}
