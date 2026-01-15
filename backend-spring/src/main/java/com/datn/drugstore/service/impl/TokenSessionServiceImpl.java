package com.datn.drugstore.service.impl;

import com.datn.drugstore.service.TokenSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenSessionServiceImpl implements TokenSessionService {

    private final RedisTemplate<String, String> redisTemplate;
    
    private static final String SESSION_PREFIX = "user:session:";
    private static final long SESSION_EXPIRATION = 24 * 60 * 60; // 24 giờ (giây)

    @Override
    public void saveActiveSession(String email, String token) {
        // Lưu token vào Redis với key = email
        // Nếu đã có session cũ, sẽ bị ghi đè (logout session cũ)
        redisTemplate.opsForValue().set(
            SESSION_PREFIX + email, 
            token, 
            SESSION_EXPIRATION, 
            TimeUnit.SECONDS
        );
    }

    @Override
    public boolean isValidSession(String email, String token) {
        String activeToken = redisTemplate.opsForValue().get(SESSION_PREFIX + email);
        // Kiểm tra token hiện tại có khớp với token trong Redis không
        return token != null && token.equals(activeToken);
    }

    @Override
    public void invalidateSession(String email) {
        // Xóa session khi logout
        redisTemplate.delete(SESSION_PREFIX + email);
    }
}
