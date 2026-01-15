package com.nhom91.drugstore.service;

import com.nhom91.drugstore.request.LoginRequest;
import com.nhom91.drugstore.request.RegisterRequest;
import com.nhom91.drugstore.request.UpdateProfileRequest;
import com.nhom91.drugstore.dto.UserDTO;
import com.nhom91.drugstore.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO login(LoginRequest loginRequest);
    UserDTO register(RegisterRequest registerRequest);
    UserDTO getProfile(Long userId);
    UserDTO updateProfile(Long userId, UpdateProfileRequest request);
    List<User> getAllUsers();
    Optional<User> findByEmail(String email);
}