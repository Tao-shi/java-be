package com.example.javabe.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new DuplicateUserException("username", user.getUsername());
        }
        if (repository.existsByEmail(user.getEmail())) {
            throw new DuplicateUserException("email", user.getEmail());
        }
        user.setId(null); // ensure a new row is inserted
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User update(Long id, User incoming) {
        User existing = findById(id);

        // Only conflict if the new username/email now belongs to a *different* user.
        if (!existing.getUsername().equals(incoming.getUsername())
                && repository.existsByUsername(incoming.getUsername())) {
            throw new DuplicateUserException("username", incoming.getUsername());
        }
        if (!existing.getEmail().equals(incoming.getEmail())
                && repository.existsByEmail(incoming.getEmail())) {
            throw new DuplicateUserException("email", incoming.getEmail());
        }

        existing.setUsername(incoming.getUsername());
        existing.setEmail(incoming.getEmail());
        existing.setFullName(incoming.getFullName());
        existing.setRole(incoming.getRole());
        existing.setPassword(passwordEncoder.encode(incoming.getPassword()));
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
    }

    // Flexible query: any combination of username, email, and role.
    public List<User> search(String username, String email, String role) {
        return repository.findAll().stream()
                .filter(u -> username == null || u.getUsername().toLowerCase().contains(username.toLowerCase()))
                .filter(u -> email == null || u.getEmail().toLowerCase().contains(email.toLowerCase()))
                .filter(u -> role == null || u.getRole().equalsIgnoreCase(role))
                .toList();
    }
}
