package io.unitbean.service.impl;

import io.unitbean.exception.UserNotFoundException;
import io.unitbean.model.User;
import io.unitbean.model.security.UserDetailsImpl;
import io.unitbean.repository.UserRepository;
import io.unitbean.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetailsImpl> userDetails = userRepository.findByUsername(username);
        return userDetails.orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }
}
