package com.nhom91.drugstore.service;

import com.nhom91.drugstore.dto.*;
import com.nhom91.drugstore.entity.User;
import com.nhom91.drugstore.repository.UserRepository;
import com.nhom91.drugstore.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Transactional
    public UserDTO login(LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String token = jwtUtils.generateToken(user);
                return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getIsAdmin(), user.getCreatedAt(), token);
            }
        }
        throw new RuntimeException("Invalid Email or Password");
    }

    @Transactional
    public UserDTO register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = userRepository.save(user);
        String token = jwtUtils.generateToken(savedUser);

        return new UserDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getIsAdmin(), savedUser.getCreatedAt(), token);
    }

    public UserDTO getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getIsAdmin(), user.getCreatedAt(), null);
    }

    @Transactional
    public UserDTO updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));

        User updatedUser = userRepository.save(user);
        String token = jwtUtils.generateToken(updatedUser);

        return new UserDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getIsAdmin(), updatedUser.getCreatedAt(), token);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Migrated from NodeJS UserRoutes logic
}