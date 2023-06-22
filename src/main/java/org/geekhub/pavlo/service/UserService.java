package org.geekhub.pavlo.service;

import org.geekhub.pavlo.model.Role;
import org.geekhub.pavlo.model.User;
import org.geekhub.pavlo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void add(User user) {
        if (user.getRole().isEmpty()) {
            user.setRole(Role.SELLER.name());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.add(user);
    }

    public void registration(User user) {
        user.setRole(Role.SELLER.name());
        add(user);
    }

    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUser(int userId) {
        return userRepository.getUser(userId);
    }

    public void deleteUser(int userId) {
        userRepository.deleteUser(userId);
    }
}
