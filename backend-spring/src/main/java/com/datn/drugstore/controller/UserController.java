package com.datn.drugstore.controller;

import com.datn.drugstore.dto.UserDTO;
import com.datn.drugstore.entity.User;
import com.datn.drugstore.exception.InvalidCredentialsException;
import com.datn.drugstore.request.LoginRequest;
import com.datn.drugstore.request.UpdateProfileRequest;
import com.datn.drugstore.response.BaseResponse;
import com.datn.drugstore.service.UserService;
import com.datn.drugstore.exception.AccountLockedException;
import com.datn.drugstore.kafka.producer.RegistrationProducer;
import com.datn.drugstore.request.RegisterRequest;
import com.datn.drugstore.utils.ResponseFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RegistrationProducer registrationProducer;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            UserDTO userDTO = userService.login(loginRequest);
            return ResponseFactory.success(userDTO, "Đăng nhập thành công");

        } catch (InvalidCredentialsException e) {
            Map<String, Object> errorData = new HashMap<>();
            errorData.put("currentAttempts", e.getCurrentAttempts());
            errorData.put("remainingAttempts", e.getRemainingAttempts());

            BaseResponse response = new BaseResponse();
            response.setCode(HttpStatus.UNAUTHORIZED.value());
            response.setMessage(e.getMessage());
            response.setData(errorData);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (AccountLockedException e) {
            Map<String, Object> lockData = new HashMap<>();
            lockData.put("lockType", e.getLockType());
            if (e.getRemainingSeconds() > 0) {
                lockData.put("remainingLockTime", e.getRemainingSeconds());
            }
            
            BaseResponse response = new BaseResponse();
            response.setCode(HttpStatus.FORBIDDEN.value());
            response.setMessage(e.getMessage());
            response.setData(lockData);
            
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    // Register endpoint - POST /api/users
    @PostMapping
    public ResponseEntity<BaseResponse> registerCompat(@Valid @RequestBody RegisterRequest registerRequest) {
        UserDTO userDTO = userService.register(registerRequest);
        registrationProducer.sendRegistrationEvent(userDTO.getEmail(), userDTO.getName());
        return ResponseFactory.success(userDTO, "Đăng ký thành công");
    }

    // Register endpoint - POST /api/users/register (Alternative)
    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserDTO userDTO = userService.register(registerRequest);
        registrationProducer.sendRegistrationEvent(userDTO.getEmail(), userDTO.getName());
        return ResponseFactory.success(userDTO, "Đăng ký thành công");
    }

    @GetMapping("/profile")
    public ResponseEntity<BaseResponse> getProfile(@AuthenticationPrincipal User user) {
        UserDTO userDTO = userService.getProfile(user.getId());
        return ResponseFactory.success(userDTO);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getUserById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        // Only allow users to view their own profile or admin to view any profile
        if (!user.getId().equals(id) && !user.getIsAdmin()) {
            return ResponseFactory.forbidden("Bạn không có quyền xem thông tin này");
        }
        UserDTO userDTO = userService.getProfile(id);
        return ResponseFactory.success(userDTO);
    }

    @PutMapping("/profile")
    public ResponseEntity<BaseResponse> updateProfile(@AuthenticationPrincipal User user,
                                                      @RequestBody UpdateProfileRequest request) {
        UserDTO userDTO = userService.updateProfile(user.getId(), request);
        return ResponseFactory.success(userDTO, "Cập nhật thành công");
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseFactory.success(users);
    }

    @GetMapping("/get-only-email/{email}")
    public ResponseEntity<BaseResponse> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            return ResponseFactory.success(user.get());
        }
        return ResponseFactory.notFound("Không tìm thấy user");
    }

}