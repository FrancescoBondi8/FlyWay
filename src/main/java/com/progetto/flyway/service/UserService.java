package com.progetto.flyway.service;

import com.progetto.flyway.model.User;
import com.progetto.flyway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Trova utente per username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Salva un nuovo utente
    public User save(User user) {
        return userRepository.save(user);
    }

}
