package com.datn.drugstore.service.impl;

import com.datn.drugstore.dto.UserDTO;
import com.datn.drugstore.request.LoginRequest;
import com.datn.drugstore.entity.User;
import com.datn.drugstore.exception.InvalidCredentialsException;
import com.datn.drugstore.repository.UserRepository;
import com.datn.drugstore.request.RegisterRequest;
import com.datn.drugstore.request.UpdateProfileRequest;
import com.datn.drugstore.service.LoginAttemptService;
import com.datn.drugstore.service.TokenSessionService;
import com.datn.drugstore.service.UserService;
import com.datn.drugstore.utils.JWTHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTHelper jwtHelper;
    private final LoginAttemptService loginAttemptService;
    private final TokenSessionService tokenSessionService;

    @Override
    public UserDTO login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        loginAttemptService.checkLockStatus(email);

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            loginAttemptService.loginFailed(email);
            int currentAttempts = loginAttemptService.getAttempts(email);
            int remainingAttempts = 3 - currentAttempts;
            throw new InvalidCredentialsException(
                    "Sai tên đăng nhập hoặc mật khẩu",
                    currentAttempts,
                    remainingAttempts
            );
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            loginAttemptService.loginFailed(email);
            int currentAttempts = loginAttemptService.getAttempts(email);
            int remainingAttempts = 3 - currentAttempts;
            throw new InvalidCredentialsException(
                    "Sai tên đăng nhập hoặc mật khẩu",
                    currentAttempts,
                    remainingAttempts
            );
        }
        loginAttemptService.loginSucceeded(email);
        String token = jwtHelper.generateToken(user.getId());
        
        // Lưu session vào Redis - logout session cũ nếu có
        tokenSessionService.saveActiveSession(email, token);

        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(),
                user.getIsAdmin(), user.getCreatedAt(), token);
    }

    @Override
    public UserDTO register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = userRepository.save(user);
        String token = jwtHelper.generateToken(savedUser.getId());
        
        // Lưu session vào Redis để token có thể được validate
        tokenSessionService.saveActiveSession(savedUser.getEmail(), token);

        return new UserDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getPhone(), savedUser.getIsAdmin(), savedUser.getCreatedAt(), token);
    }

    @Override
    public UserDTO getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getIsAdmin(), user.getCreatedAt(), null);
    }

    @Override
    @Transactional
    public UserDTO updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));

        User updatedUser = userRepository.save(user);
        String token = jwtHelper.generateToken(updatedUser.getId());
        
        // Update session with new token to prevent 401 on next request
        tokenSessionService.saveActiveSession(updatedUser.getEmail(), token);

        return new UserDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getPhone(), updatedUser.getIsAdmin(), updatedUser.getCreatedAt(), token);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
