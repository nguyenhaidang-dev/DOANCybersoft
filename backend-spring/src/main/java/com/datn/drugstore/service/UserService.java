package com.datn.drugstore.service;

import com.datn.drugstore.dto.UserDTO;
import com.datn.drugstore.request.LoginRequest;
import com.datn.drugstore.request.RegisterRequest;
import com.datn.drugstore.request.UpdateProfileRequest;
import com.datn.drugstore.entity.User;

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