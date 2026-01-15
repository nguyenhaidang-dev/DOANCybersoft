package com.nhom91.drugstore.mapper;

import com.nhom91.drugstore.dto.UserDTO;
import com.nhom91.drugstore.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDTO toDTO(User user) {
        return toDTO(user, null);
    }

    public static UserDTO toDTO(User user, String token) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getIsAdmin(),
                user.getCreatedAt(),
                token
        );
    }

    public static UserDTO toDTOWithToken(User user, String token) {
        return toDTO(user, token);
    }
}
