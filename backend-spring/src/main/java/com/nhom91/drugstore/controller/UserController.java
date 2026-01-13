package com.nhom91.drugstore.controller;

import com.nhom91.drugstore.dto.*;
import com.nhom91.drugstore.entity.User;
import com.nhom91.drugstore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginRequest loginRequest) {
        UserDTO userDTO = userService.login(loginRequest);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserDTO userDTO = userService.register(registerRequest);
        return ResponseEntity.status(201).body(userDTO);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(@AuthenticationPrincipal User user) {
        UserDTO userDTO = userService.getProfile(user.getId());
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(@AuthenticationPrincipal User user,
                                                 @RequestBody UpdateProfileRequest request) {
        UserDTO userDTO = userService.updateProfile(user.getId(), request);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get-only-email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Migrated from NodeJS UserRoutes
}