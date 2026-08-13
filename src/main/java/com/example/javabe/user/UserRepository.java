package com.example.javabe.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // Uniqueness pre-checks for friendly 409 responses.
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // Derived queries: case-insensitive partial match plus role filter.
    List<User> findByUsernameContainingIgnoreCase(String username);

    List<User> findByRoleIgnoreCase(String role);
}
