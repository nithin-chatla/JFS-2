package com.campusconnect.service;

import com.campusconnect.dto.AuthRequest;
import com.campusconnect.dto.AuthResponse;
import com.campusconnect.model.User;
import com.campusconnect.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse login(AuthRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail().trim().toLowerCase());
        if (userOpt.isEmpty()) {
            return new AuthResponse(false, "User not found with email: " + request.getEmail(), null, null);
        }

        User user = userOpt.get();
        if (!user.getPassword().equals(request.getPassword())) {
            return new AuthResponse(false, "Invalid password credentials.", null, null);
        }

        if ("INACTIVE".equalsIgnoreCase(user.getStatus())) {
            return new AuthResponse(false, "Account is disabled. Contact campus admin.", null, null);
        }

        String token = "JWT_" + UUID.randomUUID().toString().replace("-", "");
        return new AuthResponse(true, "Login successful", user, token);
    }

    public AuthResponse register(User user) {
        String email = user.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            return new AuthResponse(false, "Email is already registered!", null, null);
        }

        user.setEmail(email);
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("STUDENT");
        }
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);
        String token = "JWT_" + UUID.randomUUID().toString().replace("-", "");
        return new AuthResponse(true, "User registered successfully", saved, token);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User updated) {
        return userRepository.findById(id).map(user -> {
            user.setName(updated.getName());
            user.setDepartment(updated.getDepartment());
            user.setPhone(updated.getPhone());
            user.setBio(updated.getBio());
            user.setAvatarUrl(updated.getAvatarUrl());
            if (updated.getRole() != null) user.setRole(updated.getRole());
            if (updated.getStatus() != null) user.setStatus(updated.getStatus());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getDemoUsers() {
        return userRepository.findAll();
    }
}
